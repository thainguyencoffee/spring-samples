package com.manning.sbip.ch06.security;

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

@Configuration
public class SecurityConfiguration {

	@Bean
	InMemoryUserDetailsManager inMemoryUserDetailsManager() {
		UserDetails user = User.withUsername("user")
				.password(passwordEncoder().encode("pass"))
				.roles("USER").build();
		return new InMemoryUserDetailsManager(user);
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.requiresChannel(channelRequestMatcherRegistry -> {
					channelRequestMatcherRegistry.anyRequest().requiresSecure();
				})
				.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
					authorizationManagerRequestMatcherRegistry
							.requestMatchers("/login").permitAll()
							.anyRequest().authenticated();
				})
				.formLogin(httpSecurityFormLoginConfigurer -> {
					httpSecurityFormLoginConfigurer.loginPage("/login");
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
