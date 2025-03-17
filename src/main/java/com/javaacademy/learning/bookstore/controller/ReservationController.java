package com.javaacademy.learning.bookstore.controller;

import com.javaacademy.learning.bookstore.dto.ReservationDTO;
import com.javaacademy.learning.bookstore.dto.ReservationSearchDTO;
import com.javaacademy.learning.bookstore.dto.validation.ValidationOrder;
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
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
    public ResponseEntity<?> reserveBook(
            @PathVariable(name = "userId") Long userId,
            @PathVariable(name = "bookId") Long bookId,
            @Validated(ValidationOrder.class) @RequestBody ReservationDTO reservationDTO) {

        ReservationDTO createdReservation = reservationService.reserveBook(userId, bookId, reservationDTO.getStartDate(), reservationDTO.getEndDate());
        return ResponseEntity.ok(createdReservation);
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

    @GetMapping("/library/{libraryId}")
    public ResponseEntity<?> getReservationsForLibraryInInterval(@RequestParam(defaultValue = "0") Integer pageNumber,
                                                                 @RequestParam(defaultValue = "5") Integer numberOfElements,
                                                                 @PathVariable Long libraryId,
                                                                 @RequestParam LocalDate startDate,
                                                                 @RequestParam LocalDate endDate,
                                                                 Pageable pageable) {
        Page<Reservation> reservations = reservationService.getReservationsInAPeriod(libraryId, startDate, endDate, pageable);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<Reservation>> getUserReservations(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ASC") Sort.Direction direction) {

        Page<Reservation> reservations = reservationService.getReservationsForUserByStatus(userId, direction, page, size);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/library/filter/{libraryId}")
    public ResponseEntity<?> getLibraryReservationsByStartDateAndEndDate(@PathVariable(name = "libraryId") Long libraryId,
                                                                         @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
                                                                         @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                                         @RequestBody ReservationSearchDTO reservationsSearchDTO)  {
        Page<Reservation> reservationPage = reservationService.getLibraryReservationsByStartDateAndEndDate(libraryId, pageNumber, pageSize, reservationsSearchDTO);
        return ResponseEntity.ok(reservationPage.map(ReservationMapper::reservation2reservationDTO));
    }


    @GetMapping("/user/{userId}/filter")
    public ResponseEntity<?> getUserReservationsByStatus(@PathVariable(name = "userId") Long userId,
                                                         @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
                                                         @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                         @RequestBody ReservationSearchDTO reservationsSearchDTO){
        Page<Reservation> reservationPage = reservationService.getUserReservationsByStatus(userId, pageNumber, pageSize, reservationsSearchDTO);
        return ResponseEntity.ok(reservationPage.map(ReservationMapper::reservation2reservationDTO));
    }





}