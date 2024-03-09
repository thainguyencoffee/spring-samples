package com.manning.sbip.ch06.service;

import com.manning.sbip.ch06.dto.UserDto;
import com.manning.sbip.ch06.model.ApplicationUser;
import com.manning.sbip.ch06.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserService implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public DefaultUserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public ApplicationUser createUser(UserDto userDto) {
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setFirstName(userDto.getFirstName());
        applicationUser.setLastName(userDto.getLastName());
        applicationUser.setEmail(userDto.getEmail());
        applicationUser.setUsername(userDto.getUsername());
        applicationUser.setPassword(encoder.encode(userDto.getPassword()));
        return userRepository.save(applicationUser);
    }

    @Override
    public ApplicationUser findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
