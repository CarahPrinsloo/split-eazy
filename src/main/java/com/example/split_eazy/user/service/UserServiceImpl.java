package com.example.split_eazy.user.service;

import com.example.split_eazy.user.model.User;
import com.example.split_eazy.user.respository.UserRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public Mono<User> registerUser(User user) {
        return userRepository.findByEmailAddress(user.getEmailAddress())
                .switchIfEmpty(userRepository.findByName(user.getName()))
                .flatMap(existingUser -> Mono.error(new ValidationException("User already exists.")))
                .then(createAndSaveUser(user));
    }

    private Mono<User> createAndSaveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}