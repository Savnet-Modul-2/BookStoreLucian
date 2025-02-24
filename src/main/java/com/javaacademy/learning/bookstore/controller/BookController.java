package com.javaacademy.learning.bookstore.controller;

import com.javaacademy.learning.bookstore.dto.BookDTO;
import com.javaacademy.learning.bookstore.entities.Book;
import com.javaacademy.learning.bookstore.mapper.BookMapper;
import com.javaacademy.learning.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping("/library/{libraryId}")
    public ResponseEntity<?> create(@PathVariable Long libraryId, @RequestBody BookDTO bookDTO) {
        Book bookToCreate = BookMapper.bookDto2Book(bookDTO);
        Book createdBook = bookService.create(libraryId, bookToCreate);
        return ResponseEntity.ok(BookMapper.book2BookDto(createdBook));
    }
    @DeleteMapping("/{bookId}")
    public ResponseEntity<?> removeFromLibrary(@PathVariable Long bookId) {
        bookService.removeFromLibrary(bookId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping()
    public ResponseEntity<?> findAll() {
        List<Book> books = bookService.findAll();
        return ResponseEntity.ok(books.stream().map(BookMapper::book2BookDto).toList());
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<?> getById(@PathVariable Long bookId) {
        Book book = bookService.getById(bookId);
        return ResponseEntity.ok(BookMapper.book2BookDto(book));
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<?> updateById(@PathVariable(name = "bookId") Long bookIdToUpdate, @RequestBody BookDTO bookBody) {
        Book bookEntity = BookMapper.bookDto2Book(bookBody);
        Book updatedBook = bookService.update(bookIdToUpdate, bookEntity);
        return ResponseEntity.ok(BookMapper.book2BookDto(updatedBook));
    }
    @GetMapping("/paginated")
    public ResponseEntity<?> findAllPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Book> books = bookService.findAll(pageable);
        return ResponseEntity.ok(books.map(BookMapper::book2BookDto));
    }

}
