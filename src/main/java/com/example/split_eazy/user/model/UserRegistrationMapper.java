package com.example.split_eazy.user.model;

import com.example.split_eazy.user.controller.request.LoginUserRequest;
import com.example.split_eazy.user.controller.request.RegisterUserRequest;
import com.example.split_eazy.user.controller.response.RegisterUserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationMapper {
    public User toUser(RegisterUserRequest registerUserRequest) {
        User user = new User();

        user.setName(registerUserRequest.name());
        user.setEmailAddress(registerUserRequest.emailAddress());
        user.setPassword(registerUserRequest.password());

        return user;
    }

    public RegisterUserResponse toRegisterUserResponse(User user) {
        return new RegisterUserResponse(user.getName(), user.getEmailAddress());
    }

    public User toUser(LoginUserRequest loginUserRequest) {
        User user = new User();

        user.setName(loginUserRequest.name());
        user.setEmailAddress(loginUserRequest.emailAddress());
        user.setPassword(loginUserRequest.password());

        return user;
    }
}
