package com.javaacademy.learning.bookstore.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.javaacademy.learning.bookstore.dto.validation.AdvancedInfo;
import com.javaacademy.learning.bookstore.dto.validation.BasicInfo;
import com.javaacademy.learning.bookstore.dto.validation.ValidDate;
import com.javaacademy.learning.bookstore.entities.Exemplary;
import com.javaacademy.learning.bookstore.entities.ReservationStatus;
import com.javaacademy.learning.bookstore.entities.User;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
@ValidDate(groups = AdvancedInfo.class)
public class ReservationDTO {

    private long id;
    @NotNull(groups = BasicInfo.class)
    private LocalDate startDate;
    @NotNull(groups = BasicInfo.class)
    private LocalDate endDate;
    private ReservationStatus status;
    private UserDTO user;
    private Exemplary exemplary;
    public ReservationDTO(){}

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

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Exemplary getExemplary() {
        return exemplary;
    }

    public void setExemplary(Exemplary exemplary) {
        this.exemplary = exemplary;
    }
}