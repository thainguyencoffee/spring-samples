package com.manning.sbip.ch04.repository;

import com.manning.sbip.ch04.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    boolean existsByUsername(String username);
    User findByUsername(String username);
    User findByEmail(String email);
}
