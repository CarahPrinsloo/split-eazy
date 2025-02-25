package com.example.split_eazy.authentication.service;

import com.example.split_eazy.user.model.User;
import reactor.core.publisher.Mono;

public interface AuthenticationService {
    Mono<User> register(User user);

    String login(User user);
}
