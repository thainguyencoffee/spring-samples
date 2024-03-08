package com.manning.sbip.ch05.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final Logger logger = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // log unauthorized access
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            logger.warn("User " + authentication.getName() + " attempt access to URL: " + request.getRequestURI());
        }

        response.sendRedirect(request.getContextPath() + "/accessDenied");
    }
}
