package com.manning.sbip.ch01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.Date;

@SpringBootApplication
public class SpringBootAppDemoApplication {

	public static void main(String[] args) {
		SpringApplication springApplication =
				new SpringApplication(SpringBootAppDemoApplication.class);
		springApplication.setWebApplicationType(WebApplicationType.SERVLET);
		springApplication.run(args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void applicationReadyEvent(ApplicationReadyEvent applicationReadyEvent) {
		System.out.println("Application Ready Event generated at : " +
				new Date(applicationReadyEvent.getTimestamp()));
	}

}
