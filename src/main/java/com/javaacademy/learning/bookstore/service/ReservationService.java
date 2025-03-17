package com.javaacademy.learning.bookstore.service;


import com.javaacademy.learning.bookstore.dto.ReservationDTO;
import com.javaacademy.learning.bookstore.dto.ReservationSearchDTO;
import com.javaacademy.learning.bookstore.entities.*;
import com.javaacademy.learning.bookstore.mapper.ReservationMapper;
import com.javaacademy.learning.bookstore.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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
    @Autowired
    private LibraryRepository libraryRepository;


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

    public Page<Reservation> getReservationsInAPeriod(Long libraryId, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        libraryRepository.findById(libraryId).orElseThrow(() -> new EntityNotFoundException("Not found"));
        return reservationRepository.findAllReservationsInInterval(libraryId, startDate, endDate, pageable);
    }

    public Page<Reservation> getReservationsForUserByStatus(Long userId, Sort.Direction direction, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, "status"));
        return reservationRepository.findByUserId(userId, pageable);

    }

    public Page<Reservation> getLibraryReservationsByStartDateAndEndDate(Long libraryId, Integer pageNumber, Integer pageSize, ReservationSearchDTO reservationsSearchDTO) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "startDate"));
        return reservationRepository.findReservationsByStartDateAndEndDate(libraryId, reservationsSearchDTO.getStartDate(), reservationsSearchDTO.getEndDate(), reservationsSearchDTO.getStatusList(), pageable);
    }

    public Page<Reservation> getUserReservationsByStatus(Long userId, Integer pageNumber, Integer pageSize, ReservationSearchDTO reservationsSearchDTO) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "startDate"));
        return reservationRepository.findReservationsByUserAndReservationStatus(userId, reservationsSearchDTO.getStartDate(), reservationsSearchDTO.getEndDate(), reservationsSearchDTO.getStatusList(), pageable);
    }

}