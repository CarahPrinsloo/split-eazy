package com.example.split_eazy.authentication.controller;

import com.example.split_eazy.authentication.controller.request.LoginRequest;
import com.example.split_eazy.authentication.controller.request.RegisterRequest;
import com.example.split_eazy.authentication.controller.response.LoginResponse;
import com.example.split_eazy.authentication.controller.response.RegisterResponse;
import com.example.split_eazy.user.model.User;
import com.example.split_eazy.authentication.model.UserRegistrationMapper;
import com.example.split_eazy.authentication.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService userService;
    private final UserRegistrationMapper mapper;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerUser(
            @Valid @RequestBody final RegisterRequest registerUserRequest
    ) {
        User user = userService.register(mapper.toUser(registerUserRequest)).block();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.toRegisterUserResponse(user));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(
            @Valid @RequestBody final LoginRequest loginUserRequest
    ) {
        String jwtToken = userService.login(mapper.toUser(loginUserRequest));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new LoginResponse(jwtToken));
    }
}
