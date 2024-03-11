package com.manning.sbip.ch04.security;

import com.manning.sbip.ch04.handler.CustomAuthenticationFailureHandler;
import com.manning.sbip.ch04.service.impl.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> {
                    authorize
                            .requestMatchers("/adduser", "/login", "/login-error", "/login-verified"
                                    , "/login-disabled", "/verify/email").permitAll()
                            .anyRequest().authenticated();
                })
                .formLogin(formLoginConfig -> {
                    formLoginConfig.loginPage("/login").failureHandler(customAuthenticationFailureHandler);
                })
                .rememberMe(rememberMeConfig -> {
                    rememberMeConfig.key("remember-me-key").rememberMeCookieName("course-tracker-remember-me");
                });
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> {
            web.ignoring().requestMatchers("/webjars/**", "/images/*", "/css/*", "/h2-console/**");
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
