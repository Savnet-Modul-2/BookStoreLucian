package com.javaacademy.learning.bookstore.controller;

import com.javaacademy.learning.bookstore.dto.LibraryDTO;
import com.javaacademy.learning.bookstore.entities.Library;
import com.javaacademy.learning.bookstore.mapper.LibraryMapper;
import com.javaacademy.learning.bookstore.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/library")
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody LibraryDTO libraryDTO) {
        Library libraryToCreate = LibraryMapper.libraryDto2Library(libraryDTO);
        Library createdLibrary = libraryService.create(libraryToCreate);
        return ResponseEntity.ok(LibraryMapper.library2LibraryDto(createdLibrary));

    }
}
