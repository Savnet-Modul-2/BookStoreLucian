package com.javaacademy.learning.bookstore.mapper;


import com.javaacademy.learning.bookstore.dto.LibrarianDTO;
import com.javaacademy.learning.bookstore.entities.Librarian;

public class LibrarianMapper {

    public static LibrarianDTO librarian2LibrarianDTO(Librarian librarian) {
        if (librarian == null) {
            return null;
        }
        LibrarianDTO dto = new LibrarianDTO();
        dto.setId(librarian.getId());
        dto.setFirstName(librarian.getFirstName());
        dto.setLastName(librarian.getLastName());
        dto.setEmail(librarian.getEmail());
        dto.setPassword(librarian.getPassword());
        dto.setLibrary(LibraryMapper.library2LibraryDto(librarian.getLibrary()));
        return dto;
    }

    public static Librarian librarianDTO2Librarian(LibrarianDTO dto) {
        if (dto == null) {
            return null;
        }
        Librarian librarian = new Librarian();
        librarian.setId(dto.getId());
        librarian.setFirstName(dto.getFirstName());
        librarian.setLastName(dto.getLastName());
        librarian.setEmail(dto.getEmail());
        librarian.setPassword(dto.getPassword());
        librarian.setLibrary(LibraryMapper.libraryDto2Library(dto.getLibrary()));
        return librarian;
    }
}

