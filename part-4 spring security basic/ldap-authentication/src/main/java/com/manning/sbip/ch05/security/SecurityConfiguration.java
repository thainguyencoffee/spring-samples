package com.manning.sbip.ch05.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.ldap.EmbeddedLdapServerContextSourceFactoryBean;
import org.springframework.security.config.ldap.LdapBindAuthenticationManagerFactory;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

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

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .ldapAuthentication()
                .userDnPatterns("uid={0},ou=people")
                .contextSource()
                .url("ldap://localhost:8389/dc=manning,dc=com")
                .and()
                .passwordCompare()
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                .passwordAttribute("userPassword");
    }

}
