package com.javaacademy.learning.bookstore.repository;

import com.javaacademy.learning.bookstore.entities.Reservation;
import com.javaacademy.learning.bookstore.entities.ReservationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Query("""
            SELECT r FROM reservation r
            WHERE r.bookExemplary.book.library.id = :libraryId
            AND r.endDate <= :endDate
            AND r.startDate >= :startDate
            """)
    Page<Reservation> findAllReservationsInInterval(Long libraryId, LocalDate startDate, LocalDate endDate, Pageable pageable);

    @Query("""
               SELECT r FROM reservation r WHERE r.reservingUser.id = :userId
            """)
    Page<Reservation> findByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query(value = """
            SELECT r FROM reservation r
            WHERE (r.startDate >= :startDate AND r.endDate <= :endDate)
            AND (:reservationStatusList IS NULL OR r.status IN :reservationStatusList)
            AND r.bookExemplary.book.library.id = :libraryId
            """)
    Page<Reservation> findReservationsByStartDateAndEndDate(Long libraryId, LocalDate startDate, LocalDate endDate, List<ReservationStatus> reservationStatusList, Pageable pageable);

    @Query(value = """
            SELECT r FROM reservation r
            WHERE r.reservingUser.id = :userId
            AND (cast(:startDate as date) IS NULL OR r.startDate >= :startDate) 
            AND (cast(:endDate as date) IS NULL OR r.endDate <= :endDate)
            AND (:reservationStatusList IS NULL OR r.status IN :reservationStatusList)
            """)
    Page<Reservation> findReservationsByUserAndReservationStatus(Long userId, LocalDate startDate, LocalDate endDate, List<ReservationStatus> reservationStatusList, Pageable pageable);

}
