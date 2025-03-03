package com.javaacademy.learning.bookstore.repository;
import com.javaacademy.learning.bookstore.entities.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Page<Book> findAll(Pageable pageable);
    Optional<Book> findById(Long id);

    @Query(value = """
            SELECT book FROM book book
            WHERE (:author IS NULL OR book.author = :author)
            AND (:title IS NULL OR book.title = :title)
            """)
    Page<Book> findBooks(String author, String title, Pageable pageable);
}
