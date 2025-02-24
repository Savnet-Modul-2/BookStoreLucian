package com.javaacademy.learning.bookstore.repository;

import com.javaacademy.learning.bookstore.entities.Librarian;
import com.javaacademy.learning.bookstore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LibrarianRepository extends JpaRepository<Librarian, Long> {
    Optional<Librarian> findByEmail(String email);;
}
