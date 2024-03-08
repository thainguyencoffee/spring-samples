package com.booting.commandline;

import com.booting.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(2)
@Component
public class AnotherCommandLineRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Override
    public void run(String... args) throws Exception {
        log.info("AnotherCommandLineRunner executed as a Spring Component");
    }
}
