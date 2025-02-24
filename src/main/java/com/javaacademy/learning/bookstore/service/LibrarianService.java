package com.javaacademy.learning.bookstore.service;

import com.javaacademy.learning.bookstore.dto.LibrarianDTO;
import com.javaacademy.learning.bookstore.entities.Librarian;
import com.javaacademy.learning.bookstore.mapper.LibrarianMapper;
import com.javaacademy.learning.bookstore.repository.LibrarianRepository;
import com.javaacademy.learning.bookstore.repository.LibraryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibrarianService {
    @Autowired
    private LibrarianRepository librarianRepository;
    @Autowired
    private LibraryRepository libraryRepository;

    public LibrarianDTO create(LibrarianDTO librarianDTO) {
        Librarian newLibrarian = LibrarianMapper.librarianDTO2Librarian(librarianDTO);
        String encryptedPassword = DigestUtils.md5Hex(librarianDTO.getPassword()).toUpperCase();
        newLibrarian.setPassword(encryptedPassword);
        Librarian newLibrarianSaved = librarianRepository.save(newLibrarian);
        LibrarianDTO newLibrarianDTO = LibrarianMapper.librarian2LibrarianDTO(newLibrarianSaved);
        return newLibrarianDTO;


    }

    public Librarian login(String email, String password) {
        if (email == null || password == null) {
            throw new IllegalArgumentException("Email and password must not be null");
        }
        Librarian librarian = librarianRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        if (librarian.getPassword().equals(DigestUtils.md5Hex(password).toUpperCase())) {

            return librarian;
        }
        throw new IllegalArgumentException("Invalid credentials");
    }
}
