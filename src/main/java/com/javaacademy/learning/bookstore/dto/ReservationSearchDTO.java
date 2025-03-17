package com.javaacademy.learning.bookstore.dto;

import com.javaacademy.learning.bookstore.entities.ReservationStatus;

import java.time.LocalDate;
import java.util.List;

public class ReservationSearchDTO {
    private List<ReservationStatus> statusList;
    private LocalDate startDate;
    private LocalDate endDate;

    public List<ReservationStatus> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<ReservationStatus> statusList) {
        this.statusList = statusList;
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
}
