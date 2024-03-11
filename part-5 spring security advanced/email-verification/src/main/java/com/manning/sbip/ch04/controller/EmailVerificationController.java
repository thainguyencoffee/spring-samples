package com.manning.sbip.ch04.controller;

import com.manning.sbip.ch04.model.ApplicationUser;
import com.manning.sbip.ch04.service.UserService;
import com.manning.sbip.ch04.service.impl.EmailVerificationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Base64;

@Controller
public class EmailVerificationController {

    private final EmailVerificationService service;
    private final UserService userService;

    public EmailVerificationController(EmailVerificationService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @GetMapping("/verify/email")
    public String verifyEmail(@RequestParam String id) {
        byte[] actualId = Base64.getDecoder().decode(id.getBytes());
        String username = service.getUsernameForVerificationId(new String(actualId));
        if (username != null) {
            ApplicationUser user = userService.findByUsername(username);
            user.setVerified(true);
            userService.save(user);
            return "redirect:/login-verified";
        }
        return "redirect:/login-error";
    }
}
