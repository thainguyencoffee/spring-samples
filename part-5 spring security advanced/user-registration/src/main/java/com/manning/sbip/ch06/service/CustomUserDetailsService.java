package com.manning.sbip.ch06.service;

import com.manning.sbip.ch06.model.ApplicationUser;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser user = userService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User with username " + username + " does not exist");
        }
        UserDetails userDetails = User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles("USER")
                .disabled(false)
                .build();
        return userDetails;
    }
}
