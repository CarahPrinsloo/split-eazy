package com.example.split_eazy.users;

import com.example.split_eazy.user.model.User;
import com.example.split_eazy.user.service.UserService;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RegisterUserTests {
    @Autowired
    private UserService userService;

    @Autowired
    private R2dbcEntityTemplate r2dbcEntityTemplate;

    @AfterEach
    void cleanup() {
        // Clean database before each test
        r2dbcEntityTemplate
                .delete(User.class)
                .from("\"users\"")
                .all()
                .block();
    }

    @Test
    void shouldRegisterUserSuccessfully() {
        User newUser = new User();
        newUser.setUuid(UUID.randomUUID());
        newUser.setEmailAddress("test@example.com");
        newUser.setName("testuser");
        newUser.setPassword("password123");

        User savedUser = userService.registerUser(newUser).block();

        assertNotNull(savedUser);
        assertNotNull(savedUser.getId());
        assertNotEquals("password123", savedUser.getPassword());
    }

    @Test
    void shouldThrowWhenEmailAddressAlreadyExists() {
        User existingUser = new User();
        existingUser.setUuid(UUID.randomUUID());
        existingUser.setEmailAddress("duplicate@example.com");
        existingUser.setName("uniqueuser");
        existingUser.setPassword("password123");

        userService.registerUser(existingUser).block(); // Save first user

        User newUser = new User();
        newUser.setUuid(UUID.randomUUID());
        newUser.setEmailAddress("duplicate@example.com"); // Same email
        newUser.setName("newuser");
        newUser.setPassword("password123");

        Exception exception = assertThrows(ValidationException.class, () -> {
            userService.registerUser(newUser).block();
        });

        assertEquals("User already exists.", exception.getMessage());
    }

    @Test
    void shouldThrowWhenUserWithNameAlreadyExists() {
        User existingUser = new User();
        existingUser.setUuid(UUID.randomUUID());
        existingUser.setEmailAddress("unique@example.com");
        existingUser.setName("duplicateuser");
        existingUser.setPassword("password123");

        userService.registerUser(existingUser).block(); // Save first user

        User newUser = new User();
        newUser.setUuid(UUID.randomUUID());
        newUser.setEmailAddress("new@example.com");
        newUser.setName("duplicateuser"); // Same username
        newUser.setPassword("password123");

        Exception exception = assertThrows(ValidationException.class, () -> {
            userService.registerUser(newUser).block();
        });

        assertEquals("User already exists.", exception.getMessage());
    }
}