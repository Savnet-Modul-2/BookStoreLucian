package com.javaacademy.learning.bookstore.dto;

import com.javaacademy.learning.bookstore.entities.Book;

public class ExemplraryDTO {

    private long id;
    private String editura;
    private int durataMaximaRezervare;
    private Book book;

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
