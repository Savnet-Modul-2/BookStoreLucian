package com.javaacademy.learning.bookstore.dto;

public class ExemplaryCreateDTO {
    private String editura;
    private Integer durataMaximaRezervare;
    private Integer numarExemplare;
    private Long bookID;

    public String getEditura() {
        return editura;
    }

    public void setEditura(String editura) {
        this.editura = editura;
    }

    public Integer getDurataMaximaRezervare() {
        return durataMaximaRezervare;
    }

    public void setDurataMaximaRezervare(Integer durataMaximaRezervare) {
        this.durataMaximaRezervare = durataMaximaRezervare;
    }

    public Integer getNumarExemplare() {
        return numarExemplare;
    }

    public void setNumarExemplare(Integer numarExemplare) {
        this.numarExemplare = numarExemplare;
    }

    public Long getBookID() {
        return bookID;
    }

    public void setBookID(Long bookID) {
        this.bookID = bookID;
    }
}
