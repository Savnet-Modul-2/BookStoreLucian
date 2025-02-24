package com.javaacademy.learning.bookstore.repository;

import com.javaacademy.learning.bookstore.entities.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LibraryRepository extends JpaRepository<Library, Integer> {

    Optional<Library> findById(Long libraryId);

}
