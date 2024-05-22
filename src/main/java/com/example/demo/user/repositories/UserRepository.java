package com.example.demo.user.repositories;
import java.util.Optional;

import com.example.demo.database.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}