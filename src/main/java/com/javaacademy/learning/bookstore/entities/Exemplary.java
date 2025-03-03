package com.javaacademy.learning.bookstore.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "exemplary")
@Table(name = "Exemplaries", schema = "public")
public class Exemplary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "EDITURA")
    private String editura;
    @Column(name = "DURATA_MAXIMA_REZERVARE")
    private int durataMaximaRezervare;
    @ManyToOne
    @JoinColumn(name = "BOOK_ID")
    private Book book;
    @OneToMany(cascade = {CascadeType.PERSIST , CascadeType.MERGE, CascadeType.REMOVE},
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            mappedBy = "bookExemplary")
    @JsonManagedReference
    private List<Reservation> reservations = new ArrayList<>();

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
    public void addReservations(Reservation reservation) {
        reservations.add(reservation);
        reservation.setBookExemplary(this);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEditura() {
        return editura;
    }

    public void setEditura(String editura) {
        this.editura = editura;
    }

    public int getDurataMaximaRezervare() {
        return durataMaximaRezervare;
    }

    public void setDurataMaximaRezervare(int durataMaximaRezervare) {
        this.durataMaximaRezervare = durataMaximaRezervare;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
