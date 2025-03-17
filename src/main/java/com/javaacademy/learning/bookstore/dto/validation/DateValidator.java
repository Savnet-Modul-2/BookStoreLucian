package com.javaacademy.learning.bookstore.dto.validation;

import com.javaacademy.learning.bookstore.dto.ReservationDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateValidator implements ConstraintValidator<ValidDate, ReservationDTO> {
    @Override
    public void initialize(ValidDate constraint) {
    }
    @Override
    public boolean isValid(ReservationDTO reservationDTO, ConstraintValidatorContext context) {
        return !reservationDTO.getStartDate().isAfter(reservationDTO.getEndDate());
    }
}
