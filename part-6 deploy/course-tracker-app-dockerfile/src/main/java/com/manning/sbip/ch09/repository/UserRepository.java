package com.manning.sbip.ch09.repository;

import com.manning.sbip.ch09.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    boolean existsByUsername(String username);
    User findByUsername(String username);
    User findByEmail(String email);
}
