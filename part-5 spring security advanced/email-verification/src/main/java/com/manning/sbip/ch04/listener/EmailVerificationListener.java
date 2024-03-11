package com.manning.sbip.ch04.listener;

import com.manning.sbip.ch04.event.UserRegistrationEvent;
import com.manning.sbip.ch04.model.ApplicationUser;
import com.manning.sbip.ch04.service.impl.EmailVerificationService;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class EmailVerificationListener
        implements ApplicationListener<UserRegistrationEvent> {

    private final JavaMailSender mailSender;
    private final EmailVerificationService emailService;

    public EmailVerificationListener(JavaMailSender mailSender,
                                     EmailVerificationService emailService) {
        this.mailSender = mailSender;
        this.emailService = emailService;
    }

    @Override
    public void onApplicationEvent(UserRegistrationEvent event) {
        ApplicationUser user = event.getApplicationUser();
        String username = user.getUsername();
        String verificationId = emailService.generateVerification(username);
        String email = user.getEmail();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Course Tracker Account Verification");
        message.setText(getText(user, verificationId));
        message.setTo(email);

        System.out.println(message);

        mailSender.send(message);
    }

    private String getText (ApplicationUser user, String verificationID) {
        String encodedVerificationId = new String(Base64.getEncoder().encode(verificationID.getBytes()));
        StringBuffer buffer = new StringBuffer();
        buffer.append("Dear ").append(user.getFirstName()).append(" ").append(user.getLastName()).append(",")
                .append(System.lineSeparator()).append(System.lineSeparator());
        buffer.append("Your account has been successfully created in the Course Tracker application. ");
        buffer.append("Activate your account by clicking the following link: http://localhost:8080/verify/email?id=")
                .append(encodedVerificationId);
        buffer.append(System.lineSeparator()).append(System.lineSeparator());
        buffer.append("Regards,").append(System.lineSeparator()).append("Course Tracker Team");
        return buffer.toString();
    }
}
