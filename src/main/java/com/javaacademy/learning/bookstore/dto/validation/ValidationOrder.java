package com.javaacademy.learning.bookstore.dto.validation;

import jakarta.validation.GroupSequence;

    @GroupSequence({BasicInfo.class, AdvancedInfo.class})
    public interface ValidationOrder{

    }

