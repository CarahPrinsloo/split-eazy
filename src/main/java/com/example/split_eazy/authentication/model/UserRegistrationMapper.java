package com.example.split_eazy.authentication.model;

import com.example.split_eazy.authentication.controller.request.LoginRequest;
import com.example.split_eazy.authentication.controller.request.RegisterRequest;
import com.example.split_eazy.authentication.controller.response.RegisterResponse;
import com.example.split_eazy.user.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationMapper {
    public User toUser(RegisterRequest registerUserRequest) {
        User user = new User();

        user.setName(registerUserRequest.name());
        user.setEmailAddress(registerUserRequest.emailAddress());
        user.setPassword(registerUserRequest.password());

        return user;
    }

    public RegisterResponse toRegisterUserResponse(User user) {
        return new RegisterResponse(user.getName(), user.getEmailAddress());
    }

    public User toUser(LoginRequest loginUserRequest) {
        User user = new User();

        user.setName(loginUserRequest.name());
        user.setEmailAddress(loginUserRequest.emailAddress());
        user.setPassword(loginUserRequest.password());

        return user;
    }
}
