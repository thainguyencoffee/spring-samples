package com.manning.sbip.ch09.filter;

import com.manning.sbip.ch09.service.TotpService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class TotpAuthFilter extends GenericFilterBean {

    private final TotpService totpService;
    private static final String ON_SUCCESS_URL = "/index";
    private static final String ON_FAILURE_URL = "/totp-login-error";
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public TotpAuthFilter(TotpService totpService) {
        this.totpService = totpService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse
            , FilterChain filterChain) throws IOException, ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String code = servletRequest.getParameter("totp_code");
        if (!requiresTotpAuthentication(authentication) || code == null) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        if (code != null && totpService.verifyCode(authentication.getName(), Integer.valueOf(code))) {
            Set<String> authorities = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
            authorities.remove("TOTP_AUTH_AUTHORITY");
            authorities.add("ROLE_USER");
            authentication = new UsernamePasswordAuthenticationToken(authentication.getPrincipal()
                    , authentication.getCredentials(), buildAuthorities(authorities));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            redirectStrategy.sendRedirect((HttpServletRequest) servletRequest
                    , (HttpServletResponse) servletResponse, ON_SUCCESS_URL);
        } else {
            redirectStrategy.sendRedirect((HttpServletRequest) servletRequest
                    , (HttpServletResponse) servletResponse, ON_FAILURE_URL);
        }
    }

    private boolean requiresTotpAuthentication(Authentication authentication) {
        if (authentication == null) {
            return false;
        }
        Set<String> authorities = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        boolean hasTotpAuthority = authorities.contains("TOTP_AUTH_AUTHORITY");
        return hasTotpAuthority && authentication.isAuthenticated();
    }

    private List<GrantedAuthority> buildAuthorities (Set<String> authorities) {
        List<GrantedAuthority> authList = new ArrayList<>(1);
        for (String authority : authorities) {
            authList.add(new SimpleGrantedAuthority(authority));
        }
        return authList;
    }
}
