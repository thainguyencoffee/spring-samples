package com.manning.sbip.ch09.service.impl;

import com.manning.sbip.ch09.model.CustomUser;
import com.manning.sbip.ch09.model.User;
import com.manning.sbip.ch09.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User with username " + username + " does not exists");
        }

        SimpleGrantedAuthority simpleGrantedAuthority = null;
        // check is user enabled 2FA | yes or no
        if (user.isTotpEnabled()) {
            simpleGrantedAuthority = new SimpleGrantedAuthority("TOTP_AUTH_AUTHORITY");
        } else {
            simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_USER");
        }

        CustomUser customUser = new CustomUser(user.getUsername()
				, user.getPassword()
				, true
				, true
				, true
				, true
				, Arrays.asList(simpleGrantedAuthority));
		customUser.setTotpEnabled(user.isTotpEnabled());
		return customUser;
    }


}
