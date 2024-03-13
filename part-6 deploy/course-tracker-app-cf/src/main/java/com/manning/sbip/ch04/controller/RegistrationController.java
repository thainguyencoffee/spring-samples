package com.manning.sbip.ch04.controller;

import com.manning.sbip.ch04.model.User;
import com.manning.sbip.ch04.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class RegistrationController {

    private UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/adduser")
    public String register(Model model) {
        model.addAttribute("myUser", new User());
        return "add-user";
    }

    @PostMapping("/adduser")
    public String register(@Valid @ModelAttribute("myUser") User user, BindingResult result
            , HttpServletRequest httpRequest) {

        if(result.hasErrors()) {
            return "add-user";
        }
        userService.createUser(user);
        return "redirect:adduser?success";
    }
}
