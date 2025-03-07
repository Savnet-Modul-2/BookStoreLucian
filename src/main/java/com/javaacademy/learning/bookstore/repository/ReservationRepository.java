package com.javaacademy.learning.bookstore.repository;

import com.javaacademy.learning.bookstore.entities.Book;
import com.javaacademy.learning.bookstore.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Optional<Reservation> findById(Long reservationId);

    @Query("""
       SELECT r FROM reservation r
       WHERE r.startDate < :today
       AND r.status = 'PENDING'
       """)
    List<Reservation> findAllReservationsToBeCanceled(@Param("today") LocalDate now);

    @Query("""
       SELECT r FROM reservation r
       WHERE r.endDate < :today
       AND r.status = 'IN_PROGRESS'
       """)
    List<Reservation> findAllReservationsToBeDelayed(@Param("today") LocalDate now);

}
