package com.booting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@ConfigurationPropertiesScan(value = "com.booting")
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(Application.class);
		ConfigurableApplicationContext context = springApplication.run(args);
		AppService appService = context.getBean(AppService.class);
		log.info(appService.getAppProperties().toString());
	}

	@Bean
	CommandLineRunner commandLineRunner () {
		return args -> {
			log.info("Commandline runner executed as a bean definition with " + args.length + " arguments");
			for (int i = 0; i < args.length; i++) {
				log.info("Argument: " + args[i]);
			}
		};
	}
}
