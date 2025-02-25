package com.example.split_eazy.authentication.service;

import com.example.split_eazy.user.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.security.authentication.BadCredentialsException;

import javax.naming.AuthenticationException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LoginTests {
    @Autowired
    private AuthenticationService authService;

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
    void shouldLoginExistingUserSuccessfully() {
        String password = "password123";
        User user = registerUser(password);

        user.setPassword(password); // reset password to decrypted version
        String jwtToken = authService.login(user);
        assertNotNull(jwtToken);
    }

    @Test
    void shouldThrowIfUserPasswordMismatch() {
        User user = registerUser("password123");

        User userIncorrectPassword = new User();
        userIncorrectPassword.setName(user.getName());
        userIncorrectPassword.setPassword("password");
        userIncorrectPassword.setEmailAddress(user.getEmailAddress());

        Exception exception = assertThrows(BadCredentialsException.class, () -> {
            authService.login(userIncorrectPassword);
        });
        assertEquals("Bad credentials", exception.getMessage());
    }

    @Test
    void shouldThrowIfUserNotFound() {
        User nonExistentUser = new User();
        nonExistentUser.setName("Jane");
        nonExistentUser.setPassword("password123");
        nonExistentUser.setEmailAddress("jane.doe@gmail.com");

        Exception exception = assertThrows(BadCredentialsException.class, () -> {
            authService.login(nonExistentUser);
        });
        assertEquals("Bad credentials", exception.getMessage());
    }

    private User registerUser(String password) {
        User newUser = new User();
        newUser.setUuid(UUID.randomUUID());
        newUser.setEmailAddress("test@example.com");
        newUser.setName("testuser");
        newUser.setPassword(password);

        User savedUser = authService.register(newUser).block();

        assertNotNull(savedUser);
        assertNotNull(savedUser.getId());
        assertNotEquals(password, savedUser.getPassword());

        return newUser;
    }
}
