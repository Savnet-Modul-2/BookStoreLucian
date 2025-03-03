package com.javaacademy.learning.bookstore.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Reservations", schema = "public")
public class Reservation {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "START_DATE")
    private LocalDate startDate;
    @Column(name = "END_DATE")
    private LocalDate endDate;
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    @JsonBackReference
    private User reservingUser;
    @ManyToOne
    @JoinColumn(name = "EXEMPLARY_ID")
    @JsonBackReference
    private Exemplary bookExemplary;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public User getReservingUser() {
        return reservingUser;
    }

    public void setReservingUser(User reservingUser) {
        this.reservingUser = reservingUser;
    }

    public Exemplary getBookExemplary() {
        return bookExemplary;
    }

    public void setBookExemplary(Exemplary bookExemplary) {
        this.bookExemplary = bookExemplary;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }
}
