package com.javaacademy.learning.bookstore.mapper;

import com.javaacademy.learning.bookstore.dto.ReservationDTO;
import com.javaacademy.learning.bookstore.entities.Reservation;

public class ReservationMapper {

    public static ReservationDTO reservation2reservationDTO(Reservation reservation){
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(reservation.getId());
        reservationDTO.setStartDate(reservation.getStartDate());
        reservationDTO.setEndDate(reservation.getEndDate());
        reservationDTO.setStatus(reservation.getStatus());
        reservationDTO.setUser(UserMapper.userToUserDTO(reservation.getReservingUser()));
        reservationDTO.setExemplary(reservation.getBookExemplary());
        return reservationDTO;

    }
    public static Reservation reservationDTO2Reservation(ReservationDTO reservationDTO){
        Reservation reservation = new Reservation();
        reservation.setId(reservationDTO.getId());
        reservation.setStartDate(reservationDTO.getStartDate());
        reservation.setEndDate(reservationDTO.getEndDate());
        reservation.setStatus(reservationDTO.getStatus());
        reservation.setBookExemplary(reservationDTO.getExemplary());
        reservation.setReservingUser(UserMapper.userDtoToUser(reservationDTO.getUser()));
        return reservation;
    }

}