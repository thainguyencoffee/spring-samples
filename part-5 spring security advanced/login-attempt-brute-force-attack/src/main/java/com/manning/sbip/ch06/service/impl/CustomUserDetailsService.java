package com.manning.sbip.ch06.service.impl;

import com.manning.sbip.ch06.model.ApplicationUser;
import com.manning.sbip.ch06.service.LoginAttemptService;
import com.manning.sbip.ch06.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;
	private final LoginAttemptService loginAttemptService;

	public CustomUserDetailsService(UserService userService, LoginAttemptService loginAttemptService) {
		this.userService = userService;
		this.loginAttemptService = loginAttemptService;
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	if (loginAttemptService.isBlocked(username)) {
			throw new LockedException("User account is locked");
		}

    	ApplicationUser applicationUser = userService.findByUsername(username);
    	if(applicationUser == null) {
    		throw new UsernameNotFoundException("User with username "+username+" does not exists");
    	}
    	
    	return User.withUsername(username).password(applicationUser.getPassword()).roles("USER").disabled(!applicationUser.isVerified()).build();
    }
}
