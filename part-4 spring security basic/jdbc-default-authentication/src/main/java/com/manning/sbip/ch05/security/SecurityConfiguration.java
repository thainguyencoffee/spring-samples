package com.manning.sbip.ch05.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfiguration{

    private final DataSource dataSource;

    public SecurityConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> {
            auth
                    .requestMatchers("/login").permitAll()
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
    public UserDetailsService userDetailsService () {
        return new JdbcUserDetailsManager(dataSource);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
