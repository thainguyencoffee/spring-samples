package com.manning.sbip.ch04.controller;

import com.manning.sbip.ch04.dto.RecaptchaDto;
import com.manning.sbip.ch04.dto.UserDto;
import com.manning.sbip.ch04.event.UserRegistrationEvent;
import com.manning.sbip.ch04.model.ApplicationUser;
import com.manning.sbip.ch04.service.UserService;
import com.manning.sbip.ch04.service.impl.GoogleRecaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class RegistrationController {

    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;
    private final GoogleRecaptchaService captchaService;

    public RegistrationController(UserService userService, ApplicationEventPublisher eventPublisher
            , GoogleRecaptchaService captchaService) {
        this.userService = userService;
        this.eventPublisher = eventPublisher;
        this.captchaService = captchaService;
    }

    @Value("${app.email.verification:N}")
    private String emailVerification;

    private static int signupCount = 0;
    @PostConstruct
    public void doInit() {
        signupCount = 0;
    }

    @GetMapping("/adduser")
    public String register(Model model) {
        model.addAttribute("user", new UserDto());
        if (signupCount >= 3) {
            model.addAttribute("required_captcha", true);
        }
        return "add-user";
    }

    @PostMapping("/adduser")
    public String register(@Valid @ModelAttribute("user") UserDto userDto,  BindingResult result
            , HttpServletRequest request, Model model) {

        System.out.println("--------- " + signupCount + "  -----------");
        if (result.hasErrors()) {
            signupCount++;
            if (signupCount >= 3) {
                model.addAttribute("required_captcha", true);
            }
            return "add-user";
        }
        if (signupCount >= 3) {
            String response = request.getParameter("g-recaptcha-response");
            if (response == null) {
                return "add-user";
            }
            String ip = request.getRemoteAddr();
            System.out.println(response);
            RecaptchaDto recaptchaDto = captchaService.verify(ip, response);
            if (!recaptchaDto.isSuccess()) {
                return "redirect:adduser?incorrectRECAPTCHA";
            } else {
                signupCount = 0;
                model.addAttribute("required_captcha", false);
            }
        }

        ApplicationUser applicationUser = userService.createUser(userDto);
        if ("Y".equalsIgnoreCase(emailVerification)) {
            eventPublisher.publishEvent(new UserRegistrationEvent(applicationUser));
            return "redirect:adduser?validate";
        }
        return "redirect:adduser?success";
    }
}
