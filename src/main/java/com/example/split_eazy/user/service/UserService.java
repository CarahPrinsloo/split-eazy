package com.example.split_eazy.user.service;

import com.example.split_eazy.user.model.User;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<User> registerUser(User user);

    String login(User user);
}
