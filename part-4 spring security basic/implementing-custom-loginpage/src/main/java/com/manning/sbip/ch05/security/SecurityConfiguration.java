package com.manning.sbip.ch05.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.util.List;

//@Configuration
public class SecurityConfiguration {

//    @Autowired
    private AccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> {
            auth
                    .requestMatchers("/login").permitAll()
                    .requestMatchers("/delete/**").hasRole("ADMIN")
                    .anyRequest().authenticated();
        }).formLogin(formLoginConfigurer -> {
            formLoginConfigurer.loginPage("/login");
        }).exceptionHandling(exHandlingConf -> {
            exHandlingConf.accessDeniedHandler(customAccessDeniedHandler);
        });
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> {
            web.ignoring().requestMatchers("/webjars/**", "/images/**"
                    , "/css/**", "/h2-console/**");
        };
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user1 = User
                .withUsername("user")
                .password(passwordEncoder().encode("p@ssw0rd"))
                .roles("USER")
                .build();
        UserDetails user2 = User
                .withUsername("admin")
                .password(passwordEncoder().encode("p@ssw0rd"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(List.of(user1, user2));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
