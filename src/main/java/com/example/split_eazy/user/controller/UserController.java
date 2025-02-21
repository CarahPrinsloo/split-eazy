package com.example.split_eazy.user.controller;

import com.example.split_eazy.user.controller.request.LoginUserRequest;
import com.example.split_eazy.user.controller.request.RegisterUserRequest;
import com.example.split_eazy.user.controller.response.LoginUserResponse;
import com.example.split_eazy.user.controller.response.RegisterUserResponse;
import com.example.split_eazy.user.model.User;
import com.example.split_eazy.user.model.UserRegistrationMapper;
import com.example.split_eazy.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserRegistrationMapper mapper;

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> registerUser(
            @Valid @RequestBody final RegisterUserRequest registerUserRequest
    ) {
        User user = userService.registerUser(mapper.toUser(registerUserRequest)).block();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapper.toRegisterUserResponse(user));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginUserResponse> loginUser(
            @Valid @RequestBody final LoginUserRequest loginUserRequest
    ) {
        String jwtToken = userService.login(mapper.toUser(loginUserRequest));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new LoginUserResponse(jwtToken));
    }
}
