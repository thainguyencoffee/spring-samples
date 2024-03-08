package com.booting;

import com.booting.model.User;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Set;


@SpringBootApplication
public class Application implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User user1 = new User("user", "BBAACC$$");
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

		Set<ConstraintViolation<User>> violations = validator.validate(user1);
		if (violations.isEmpty()) {
			logger.info("Password for user adhere the password policy");
		}
		else {
			violations.forEach(userConstraintViolation -> logger.error("Violation details: [{}]"
					, userConstraintViolation.getMessage()));
		}
	}
}
