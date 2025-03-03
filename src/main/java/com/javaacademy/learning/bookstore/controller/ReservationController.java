package com.javaacademy.learning.bookstore.controller;

import com.javaacademy.learning.bookstore.entities.Book;
import com.javaacademy.learning.bookstore.entities.Reservation;
import com.javaacademy.learning.bookstore.entities.ReservationStatus;
import com.javaacademy.learning.bookstore.mapper.ReservationMapper;
import com.javaacademy.learning.bookstore.repository.ReservationRepository;
import com.javaacademy.learning.bookstore.service.LibrarianService;
import com.javaacademy.learning.bookstore.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private LibrarianService librarianService;
    @Autowired
    private ReservationRepository reservationRepository;

    @GetMapping
    public ResponseEntity<Page<Book>> findBooksByTitleAndAuthor(
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "5") Integer numberOfElements,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String title,
            Pageable pageable) {

        Page<Book> books = reservationService.getBooks(author, title, pageable);
        return ResponseEntity.ok(books);
    }

    @PostMapping("/{userId}/{bookId}")
    public ResponseEntity<?> reserveBook(@PathVariable(name = "userId") Long userId, @PathVariable(name = "bookId") Long bookId, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        Reservation createdReservation = reservationService.reserveBook(userId, bookId, startDate, endDate);
        return ResponseEntity.ok(ReservationMapper.reservation2reservationDTO(createdReservation));
    }

    @PutMapping("/{reservationId}")
    public ResponseEntity<?> updateStatusReservation(@PathVariable(name = "reservationId") Long reservationId, @RequestParam ReservationStatus status) {

        Reservation reservationToUpdate = reservationRepository.findById(reservationId).orElse(null);

        if (reservationToUpdate == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Reservation with ID " + reservationId + " not found.");
        }

        if (!reservationToUpdate.getStatus().isNextState(status)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid status transition: " + reservationToUpdate.getStatus() + " -> " + status);
        }

        reservationToUpdate.setStatus(status);
        Reservation updatedReservation = reservationRepository.save(reservationToUpdate);

        return ResponseEntity.ok(ReservationMapper.reservation2reservationDTO(updatedReservation));
    }
}
