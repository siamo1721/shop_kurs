package com.example.kurs.repository;

import java.util.Optional;

import com.example.kurs.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
