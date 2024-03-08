package com.boot.ch04.listener;

import com.boot.ch04.exception.UrlNotAccessibleException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UrlAccessibilityHandler {

    @Value("${api.url}")
    private String url;

    @EventListener(classes = ContextRefreshedEvent.class)
    public void listen() {
        // For demonstration purpose, we are throwing
        // the exception assuming the site is not reachable
        throw new UrlNotAccessibleException(url);
    }
}
