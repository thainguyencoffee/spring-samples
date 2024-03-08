package com.manning.sbip.ch05;

import com.manning.sbip.ch05.model.ApplicationUser;
import com.manning.sbip.ch05.repository.ApplicationUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestRepository {

    @Autowired
    ApplicationUserRepository repo;

    @Test
    void test() {
        ApplicationUser user = repo.findByUsername("jsocket");
        System.out.println(user);
    }
}
