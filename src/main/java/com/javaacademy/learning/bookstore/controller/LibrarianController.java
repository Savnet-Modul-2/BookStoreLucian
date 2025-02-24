package com.javaacademy.learning.bookstore.controller;

import com.javaacademy.learning.bookstore.dto.LibrarianDTO;
import com.javaacademy.learning.bookstore.dto.LibraryDTO;
import com.javaacademy.learning.bookstore.dto.LoginRequest;
import com.javaacademy.learning.bookstore.entities.Librarian;
import com.javaacademy.learning.bookstore.entities.Library;
import com.javaacademy.learning.bookstore.mapper.LibraryMapper;
import com.javaacademy.learning.bookstore.service.LibrarianService;
import com.javaacademy.learning.bookstore.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.javaacademy.learning.bookstore.service.LibrarianService;
import com.javaacademy.learning.bookstore.mapper.LibrarianMapper;

@RestController
@RequestMapping("/librarian")
public class LibrarianController {
    @Autowired
    private LibrarianService librarianService;
    @PostMapping()
    public ResponseEntity<?> create(@RequestBody LibrarianDTO librarianDTO){
        LibrarianDTO createdLibrarian = librarianService.create(librarianDTO);
        return ResponseEntity.ok(createdLibrarian);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Librarian loggedLibrarian = librarianService.login(loginRequest.getEmail(), loginRequest.getPassword());
        return ResponseEntity.ok(loggedLibrarian.getId().toString());
    }
}