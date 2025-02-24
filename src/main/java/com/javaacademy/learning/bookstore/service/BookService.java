package com.javaacademy.learning.bookstore.service;

import com.javaacademy.learning.bookstore.entities.Book;
import com.javaacademy.learning.bookstore.entities.Library;
import com.javaacademy.learning.bookstore.repository.BookRepository;
import com.javaacademy.learning.bookstore.repository.LibraryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private LibraryRepository libraryRepository;


    public Book create(Long libraryId, Book book) {
        Library library = libraryRepository.findById(libraryId)
                .orElseThrow(() -> new EntityNotFoundException("Library not found"));

        book.setLibrary(library);
        return bookRepository.save(book);
    }
    public void removeFromLibrary(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("Book not found"));

        book.setLibrary(null);
        bookRepository.save(book);
    }

    public Book getById(Long bookIdToSearchFor) {
        return bookRepository.findById(bookIdToSearchFor)
                .orElseThrow(EntityNotFoundException::new);
    }
    public List<Book> findAll() {
        return bookRepository.findAll();
    }
    public Page<Book> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    public Book update(Long bookIdToUpdate, Book book) {
        Book updatedBook = bookRepository.findById(bookIdToUpdate)
                .orElseThrow(EntityNotFoundException::new);

        updatedBook.setTitle(book.getTitle());
        updatedBook.setIsbn(book.getIsbn());
        updatedBook.setAuthor(book.getAuthor());
        updatedBook.setAppearanceDate(book.getAppearanceDate());
        updatedBook.setNrOfPages(book.getNrOfPages());
        updatedBook.setCategory(book.getCategory());
        updatedBook.setLanguage(book.getLanguage());

        return bookRepository.save(updatedBook);
    }


}