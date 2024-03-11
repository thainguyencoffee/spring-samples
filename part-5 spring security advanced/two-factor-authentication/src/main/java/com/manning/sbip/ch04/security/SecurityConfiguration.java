package com.manning.sbip.ch04.security;

import com.manning.sbip.ch04.filter.TotpAuthFilter;
import com.manning.sbip.ch04.service.DefaultAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {

    private final TotpAuthFilter totpAuthFilter;

    public SecurityConfiguration(TotpAuthFilter totpAuthFilter) {
        this.totpAuthFilter = totpAuthFilter;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(totpAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .requiresChannel(channelRequestMatcherRegistry -> {
                    channelRequestMatcherRegistry.anyRequest().requiresSecure();
                })
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
                    authorizationManagerRequestMatcherRegistry
                            .requestMatchers("/login", "/adduser"
                                    , "login-error", "setup-totp"
                                    , "/confirm-totp").permitAll()
                            .requestMatchers("/totp-login", "/totp-error").hasAuthority("TOTP_AUTH_AUTHORITY")
                            .anyRequest().hasRole("USER");
                })
                .formLogin(formLoginConfigure -> {
                    formLoginConfigure
                            .loginPage("/login")
                            .successHandler(new DefaultAuthenticationSuccessHandler())
                            .failureUrl("/login-error");
                });
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer securityCustomizer() {
        return web -> {
            web.ignoring().requestMatchers("/webjars/**", "/images/*", "/css/*", "/h2-console/**");
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
