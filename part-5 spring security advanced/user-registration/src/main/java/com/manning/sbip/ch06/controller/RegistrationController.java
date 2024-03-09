package com.manning.sbip.ch06.controller;

import com.manning.sbip.ch06.dto.UserDto;
import com.manning.sbip.ch06.model.ApplicationUser;
import com.manning.sbip.ch06.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Objects;

@Controller
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/adduser")
    public String register(Model model) {
        model.addAttribute("user", new UserDto());
        return "add-user";
    }

    @PostMapping("/adduser")
    public String register(@Valid @ModelAttribute("user") UserDto user, BindingResult result) {
        if (result.hasErrors()) {
            return "add-user";
        }
        if (!user.getConfirmPassword().equals(user.getPassword())) {
            result.addError(new FieldError("userDto", "confirmPassword", "confirm password isn't same to password"));
            return "add-user";
        }
        userService.createUser(user);
        return "redirect:adduser?success";
    }

}

