package com.manning.sbip.ch04.service;

import com.manning.sbip.ch04.exception.InvalidVerificationCode;
import com.manning.sbip.ch04.model.TotpDetails;
import com.manning.sbip.ch04.model.CustomUser;
import com.manning.sbip.ch04.model.User;
import com.manning.sbip.ch04.repository.TotpRepository;
import com.manning.sbip.ch04.repository.UserRepository;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TotpService {

    private final GoogleAuthenticator googleAuth = new GoogleAuthenticator();
    private final TotpRepository totpRepository;
    private final UserRepository userRepository;
    private static final String ISSUER = "CourseTracker";

    public TotpService(TotpRepository totpRepository, UserRepository userRepository) {
        this.totpRepository = totpRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public String generateAuthenticationQrUrl(String username) {
        GoogleAuthenticatorKey authenticatorKey = googleAuth.createCredentials();
        String secret = authenticatorKey.getKey();
        totpRepository.deleteByUsername(username);
        totpRepository.save(new TotpDetails(username, secret));
        return GoogleAuthenticatorQRGenerator.getOtpAuthURL(ISSUER, username, authenticatorKey);
    }

    public boolean isTotpEnabled(String username) {
        return userRepository.findByUsername(username).isTotpEnabled();
    }

    // Validates whether the OTP supplied is correct and valid and ensures that user has configured 2FA
    public void enableTotpForUser(String username, int code) {
        if (!verifyCode(username, code)) {
            throw new InvalidVerificationCode("Invalid verification code");
        }

        User user = userRepository.findByUsername(username);
        user.setTotpEnabled(true);
        userRepository.save(user);
    }

    public boolean verifyCode(String username, int verificationCode) {
        TotpDetails totpDetails = totpRepository.findByUsername(username);
        return googleAuth.authorize(totpDetails.getSecret(), verificationCode);
    }
}
