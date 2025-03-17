package com.javaacademy.learning.bookstore.cronjobs;

import com.javaacademy.learning.bookstore.entities.Reservation;
import com.javaacademy.learning.bookstore.entities.ReservationStatus;
import com.javaacademy.learning.bookstore.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.util.List;

@Configuration
@EnableScheduling
public class CronJobs {

    @Autowired
    private ReservationRepository reservationRepository;

    @Scheduled(cron = "0 */5 * * * *")
    public void reservationsCronJobs() {
        LocalDate now = LocalDate.now();
        List<Reservation> reservationsCancel = reservationRepository.findAllReservationsToBeCanceled(now);
        List<Reservation> reservationsDelay = reservationRepository.findAllReservationsToBeDelayed(now);
        reservationsCancel.forEach(reservation -> {
            reservation.setStatus(ReservationStatus.CANCELED);
        });
        reservationsDelay.forEach(reservation -> {
            reservation.setStatus(ReservationStatus.DELAYED);
        });
        reservationRepository.saveAll(reservationsCancel);
        reservationRepository.saveAll(reservationsDelay);
    }
}
