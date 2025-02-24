package com.javaacademy.learning.bookstore.service;

import com.javaacademy.learning.bookstore.dto.UserDTO;
import com.javaacademy.learning.bookstore.emailsender.EmailDetails;
import com.javaacademy.learning.bookstore.emailsender.EmailService;
import com.javaacademy.learning.bookstore.entities.User;
import com.javaacademy.learning.bookstore.mapper.UserMapper;
import com.javaacademy.learning.bookstore.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.UUID;

import java.util.Optional;
import java.util.Random;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;

    public UserDTO createUser(UserDTO userDTO) {
        User newUser = UserMapper.userDtoToUser(userDTO);
        String encryptedPassword = DigestUtils
                .md5Hex(newUser.getPassword()).toUpperCase();
        newUser.setPassword(encryptedPassword);
        newUser.setMailCode(RandomStringGenerator.generateRandomString());
        User newUserSaved = userRepository.save(newUser);
        UserDTO newUserDTO = UserMapper.userToUserDTO(newUserSaved);
        emailService.sendSimpleMail(new EmailDetails(userDTO.getEmail(), newUserSaved.getMailCode(), "Cod mail"));
        return newUserDTO;

    }

    public UserDTO getById(long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        User user = userOptional.get();
        return UserMapper.userToUserDTO(user);
    }

    @Transactional
    public User verifyUser(Long userId, String introducedCode) {
        User user = userRepository.findById(userId).get();
        if (user.getMailCode().equals(introducedCode)) {
            user.setVerifiedAccount(true);
            userRepository.save(user);
        }
        return user;
    }
    public User login(String email, String password) {
        if (email == null || password == null) {
            throw new IllegalArgumentException("Email and password must not be null");
        }
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        if (user.getPassword().equals(DigestUtils.md5Hex(password).toUpperCase())
                && user.isVerifiedAccount()) {
            return user;
        }
        throw new IllegalArgumentException("Invalid credentials");
    }



    //}
}

class RandomStringGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int STRING_LENGTH = 6;
    private static final SecureRandom random = new SecureRandom();

    public static String generateRandomString() {
        StringBuilder sb = new StringBuilder(STRING_LENGTH);
        for (int i = 0; i < STRING_LENGTH; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
}
