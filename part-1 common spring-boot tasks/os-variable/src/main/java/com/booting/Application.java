package com.booting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext context =
				SpringApplication.run(Application.class, args);
		Environment env = context.getBean(Environment.class);
		log.info(env.getProperty("app.timeout"));
	}

}
