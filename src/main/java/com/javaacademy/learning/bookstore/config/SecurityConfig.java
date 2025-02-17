package com.javaacademy.learning.bookstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Disable CSRF for APIs
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/users/**").permitAll()
                        .requestMatchers("/sendMail").permitAll()// Allow public access to /users
                        .anyRequest().authenticated()  // Require authentication for other endpoints
                )
                .formLogin(Customizer.withDefaults())  // Enable form login if needed
                .httpBasic(Customizer.withDefaults());  // Enable Basic Auth if needed

        return http.build();
    }
}
