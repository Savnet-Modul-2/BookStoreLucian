package com.javaacademy.learning.bookstore.service;

import com.javaacademy.learning.bookstore.entities.Book;
import com.javaacademy.learning.bookstore.entities.Exemplary;
import com.javaacademy.learning.bookstore.repository.BookRepository;
import com.javaacademy.learning.bookstore.repository.ExemplaryRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExemplaryService {
    private final BookRepository bookRepository;
    private ExemplaryRepository exemplaryRepository;
    private BookService bookService;

    @Autowired
    public ExemplaryService(ExemplaryRepository exemplaryRepository, BookService bookService, BookRepository bookRepository) {
        this.exemplaryRepository = exemplaryRepository;
        this.bookService = bookService;
        this.bookRepository = bookRepository;
    }

    public List<Exemplary> create(List<Exemplary> exemplariesToCreate, Long bookID) {
        Book book = bookRepository.findById(bookID).get();
        exemplariesToCreate.forEach(exemplary -> {
            exemplary.setBook(book);
            exemplaryRepository.save(exemplary);
        });
        return exemplariesToCreate;
    }

    public Page<Exemplary> listPaginated(Integer pageNumber, Integer numberOfElements) {
        if (pageNumber != null && numberOfElements != null) {
            Pageable pageable = PageRequest.of(pageNumber, numberOfElements);
            return exemplaryRepository.findAll(pageable);
        }
        return exemplaryRepository.findAll(Pageable.unpaged());
    }

    @Transactional
    public void delete(Long id) {
        if (!exemplaryRepository.existsById(id)) {
            throw new EntityNotFoundException("Exemplary with ID " + id + " not found");
        }
        exemplaryRepository.deleteById(id);
    }

}
