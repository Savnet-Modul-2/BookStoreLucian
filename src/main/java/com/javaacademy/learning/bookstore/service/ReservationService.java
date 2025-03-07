package com.javaacademy.learning.bookstore.service;


import com.javaacademy.learning.bookstore.dto.ReservationDTO;
import com.javaacademy.learning.bookstore.entities.*;
import com.javaacademy.learning.bookstore.mapper.ReservationMapper;
import com.javaacademy.learning.bookstore.repository.BookRepository;
import com.javaacademy.learning.bookstore.repository.ExemplaryRepository;
import com.javaacademy.learning.bookstore.repository.ReservationRepository;
import com.javaacademy.learning.bookstore.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ExemplaryRepository exemplaryRepository;


    public Page<Book> getBooks(String author, String title, Pageable pageable) {
        return bookRepository.findBooks(author, title, pageable);
    }
    public ReservationDTO reserveBook(Long userId, Long bookId, LocalDate startDate, LocalDate endDate) {
        User user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        Book book = bookRepository.findById(bookId).orElseThrow(EntityNotFoundException::new);
        Exemplary availableExemplary = exemplaryRepository.findAvailableExemplary(bookId, startDate, endDate).orElseThrow(EntityNotFoundException::new);
        Reservation reservation = new Reservation();
        reservation.setStartDate(startDate);
        reservation.setEndDate(endDate);
        reservation.setStatus(ReservationStatus.PENDING);
        user.addReservation(reservation);
        availableExemplary.addReservations(reservation);
        Reservation newReservationSaved = reservationRepository.save(reservation);
        ReservationDTO newReservationDTO = ReservationMapper.reservation2reservationDTO(newReservationSaved);
        return newReservationDTO;
    }

}