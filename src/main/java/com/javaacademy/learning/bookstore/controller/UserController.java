package com.javaacademy.learning.bookstore.controller;

import com.javaacademy.learning.bookstore.dto.LoginRequest;
import com.javaacademy.learning.bookstore.dto.UserDTO;
import com.javaacademy.learning.bookstore.dto.VerificationRequest;
import com.javaacademy.learning.bookstore.entities.User;
import com.javaacademy.learning.bookstore.mapper.UserMapper;
import com.javaacademy.learning.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        UserDTO createdUser = userService.createUser(userDTO);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable long id) {
        UserDTO foundUser = userService.getById(id);
        return ResponseEntity.ok(foundUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> verify(@PathVariable long id, @RequestBody VerificationRequest request) {
        User verified = userService.verifyUser(id, request.getCode());
        return ResponseEntity.ok(UserMapper.userToUserDTO(verified));

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        User loggedUser = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
        return ResponseEntity.ok(loggedUser.getId().toString());
    }


}
