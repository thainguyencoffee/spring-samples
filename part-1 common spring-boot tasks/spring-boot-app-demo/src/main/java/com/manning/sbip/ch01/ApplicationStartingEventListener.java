package com.manning.sbip.ch01;

import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

import java.util.Date;

public class ApplicationStartingEventListener
        implements ApplicationListener<ApplicationStartingEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartingEvent event) {
        System.out.println("Application Starting Event logged at: " + new Date(event.getTimestamp()));
    }
}
