package com.manning.sbip.ch06.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.manning.sbip.ch06.handler.CustomAuthenticationFailureHandler;
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
                            .requestMatchers("/adduser", "/login", "/login-error", "/login-verified"
                                    , "/login-disabled", "/verify/email", "login-locked").permitAll()
                            .anyRequest().authenticated();
                })
                .formLogin(formLoginConfigure -> {
                    formLoginConfigure
                            .loginPage("/login")
                            .failureHandler(customAuthenticationFailureHandler);
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
