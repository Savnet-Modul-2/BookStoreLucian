package com.javaacademy.learning.bookstore.repository;

import com.javaacademy.learning.bookstore.entities.Book;
import com.javaacademy.learning.bookstore.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findById(Long reservationId);


}
