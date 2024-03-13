package com.manning.sbip.ch09.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
public class DefaultAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        if (isTotpAuthRequired(authentication)) {
            redirectStrategy.sendRedirect(request, response, "/totp-login");
        } else {
            redirectStrategy.sendRedirect(request, response, "/account");
        }
    }

    private boolean isTotpAuthRequired(Authentication authentication) {
        Set<String> authorities = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        return authorities.contains("TOTP_AUTH_AUTHORITY");
    }
}
