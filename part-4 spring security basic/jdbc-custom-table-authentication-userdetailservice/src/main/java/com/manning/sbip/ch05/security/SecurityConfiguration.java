package com.manning.sbip.ch05.security;

import com.manning.sbip.ch05.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfiguration(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> {
            auth
                    .requestMatchers("/login").permitAll()
                    .requestMatchers("/delete/**").hasRole("ADMIN")
                    .anyRequest().authenticated();
        }).formLogin(formLogin -> {
            formLogin.loginPage("/login");
        });
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() throws Exception {
        return web -> {
            web.ignoring().requestMatchers("/webjars/**", "/images/**", "/css/**", "/h2-console/**");
        };
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
