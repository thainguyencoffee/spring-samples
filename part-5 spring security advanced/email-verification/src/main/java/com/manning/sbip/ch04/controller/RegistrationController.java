package com.manning.sbip.ch04.controller;

import com.manning.sbip.ch04.dto.UserDto;
import com.manning.sbip.ch04.event.UserRegistrationEvent;
import com.manning.sbip.ch04.model.ApplicationUser;
import com.manning.sbip.ch04.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
public class RegistrationController {

    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;

    public RegistrationController(UserService userService, ApplicationEventPublisher eventPublisher) {
        this.userService = userService;
        this.eventPublisher = eventPublisher;
    }

    @GetMapping("/adduser")
    public String register(Model model) {
        model.addAttribute("user", new UserDto());
        return "add-user";
    }

    @PostMapping("/adduser")
    public String register(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result) {
        if(result.hasErrors()) {
            return "add-user";
        }
        ApplicationUser applicationUser = userService.createUser(userDto);
        eventPublisher.publishEvent(new UserRegistrationEvent(applicationUser));
        return "redirect:adduser?validate";
    }
}
