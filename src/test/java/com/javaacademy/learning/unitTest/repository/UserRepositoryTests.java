package com.javaacademy.learning.unitTest.repository;

import com.javaacademy.learning.bookstore.entities.User;
import com.javaacademy.learning.bookstore.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;
    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setEmail("test@gmail.com");
    }

    @AfterEach
    public void tearDown(){
        userRepository.deleteAll();
    }

    @Test
    public void givenEmail_findByEmail_returnUser(){
        String testEmail = "test@gmail.com";

        userRepository.save(testUser);
        User expected = userRepository.findByEmail(testEmail).orElse(null);
        Assertions.assertThat(expected).isEqualTo(testUser);
    }

}
