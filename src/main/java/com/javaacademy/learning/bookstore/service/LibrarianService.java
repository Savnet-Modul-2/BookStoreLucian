package com.javaacademy.learning.bookstore.service;

import com.javaacademy.learning.bookstore.dto.LibrarianDTO;
import com.javaacademy.learning.bookstore.dto.ReservationDTO;
import com.javaacademy.learning.bookstore.entities.Librarian;
import com.javaacademy.learning.bookstore.entities.Reservation;
import com.javaacademy.learning.bookstore.entities.ReservationStatus;
import com.javaacademy.learning.bookstore.mapper.LibrarianMapper;
import com.javaacademy.learning.bookstore.repository.LibrarianRepository;
import com.javaacademy.learning.bookstore.repository.LibraryRepository;
import com.javaacademy.learning.bookstore.repository.ReservationRepository;
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
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ReservationRepository reservationRepository;

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

    public Reservation updateReservationStatus(Long reservationId, ReservationStatus reservationStatus) {
        Reservation reservationToUpdate = reservationRepository.findById(reservationId).orElse(null);

        if (reservationToUpdate == null) {
            throw new EntityNotFoundException("Reservation not found.");
        }

        if (reservationToUpdate.getStatus().isNextState(reservationStatus)) {
            reservationToUpdate.setStatus(reservationStatus);
            return reservationRepository.save(reservationToUpdate);
        } else {
            throw new IllegalStateException("Invalid transition: " + reservationToUpdate.getStatus() + " -> " + reservationStatus);
        }
    }


}
