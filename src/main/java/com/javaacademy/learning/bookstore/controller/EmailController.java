package com.javaacademy.learning.bookstore.controller;

import com.javaacademy.learning.bookstore.emailsender.EmailDetails;
import com.javaacademy.learning.bookstore.emailsender.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
    @Autowired private EmailService emailService;

    @PostMapping("/sendMail")
    public void sendMail(@RequestBody EmailDetails details) {
        emailService.sendSimpleMail(details);
    }

}
