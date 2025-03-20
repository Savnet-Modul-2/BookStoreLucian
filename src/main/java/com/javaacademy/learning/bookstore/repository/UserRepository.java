package com.javaacademy.learning.bookstore.repository;

import com.javaacademy.learning.bookstore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(long userId);

    Optional<User> findByEmail(String email);
}
