package com.manning.sbip.ch04.security;

import com.manning.sbip.ch04.service.impl.Oauth2AuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
                    authorizationManagerRequestMatcherRegistry
                            .requestMatchers("/login", "/adduser", "login-error").permitAll()
                            .anyRequest().authenticated();
                })
                .formLogin(formLoginConfigure -> {
                    formLoginConfigure
                            .loginPage("/login")
                            .failureUrl("/login-error");
                })
                .oauth2Login(oauth2LoginConfigure -> {
                    oauth2LoginConfigure
                            .loginPage("/login")
                            .successHandler(new Oauth2AuthenticationSuccessHandler());
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
