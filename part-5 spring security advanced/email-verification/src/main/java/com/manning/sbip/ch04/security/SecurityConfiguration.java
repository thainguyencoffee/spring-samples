package com.manning.sbip.ch04.security;

import com.manning.sbip.ch04.handler.CustomAuthenticationFailureHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    public SecurityConfiguration(CustomAuthenticationFailureHandler customAuthenticationFailureHandler) {
        this.customAuthenticationFailureHandler = customAuthenticationFailureHandler;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .requiresChannel(channelRequestMatcherRegistry -> {
                    channelRequestMatcherRegistry.anyRequest().requiresSecure();
                })
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
                    authorizationManagerRequestMatcherRegistry
                            .requestMatchers("/login", "/adduser"
                                    , "login-error", "login-verified"
                                    , "/login-disabled", "verify/email").permitAll()
                            .anyRequest().authenticated();
                })
                .formLogin(httpSecurityFormLoginConfigurer -> {
                    httpSecurityFormLoginConfigurer
                            .loginPage("/login")
                            .failureHandler(customAuthenticationFailureHandler);
                });
        return http.build();
    }

    @Bean
    WebSecurityCustomizer webSecurityConfig() {
        return web -> web.ignoring()
                .requestMatchers("/webjars/**", "/images/*", "/css/*", "/h2-console/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
