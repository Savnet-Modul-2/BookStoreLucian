package com.javaacademy.learning.bookstore.repository;

import com.javaacademy.learning.bookstore.entities.Exemplary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ExemplaryRepository extends JpaRepository<Exemplary, Long> {
    Optional<Exemplary> findById(long id);

    //@Query(value = """
    //      SELECT * FROM exemplaries e
    //    WHERE e.ID NOT IN (
    //      SELECT r.EXEMPLARY_ID FROM reservations r
    //    WHERE (r.START_DATE <= :endDate OR r.END_DATE >= :startDate)
    //  AND r.STATUS IN ('IN_PROGRESS', 'PENDING')
    //)
    //AND e.BOOK_ID = :bookId
    //LIMIT 1
    //""", nativeQuery = true)

    @Query(value = """
        SELECT * FROM exemplaries exemplary
        WHERE exemplary.book_id = :bookId
        AND exemplary.id NOT IN (
            SELECT reservation.exemplary_id FROM reservations reservation
            WHERE reservation.exemplary_id = exemplary.id
            AND NOT (reservation.end_date < :startDate OR reservation.start_date > :endDate)
        )
        LIMIT 1
    """, nativeQuery = true)

    Optional<Exemplary> findAvailableExemplary(Long bookId, LocalDate startDate, LocalDate endDate);
}