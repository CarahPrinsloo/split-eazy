package com.example.split_eazy.authentication.service;

import com.example.split_eazy.security.service.JwtService;
import com.example.split_eazy.user.model.User;
import com.example.split_eazy.user.respository.UserRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService,
            AuthenticationManager authenticationManager
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    @Transactional
    public Mono<User> register(User user) {
        return userRepository.findByEmailAddress(user.getEmailAddress())
                .switchIfEmpty(userRepository.findByName(user.getName()))
                .flatMap(existingUser -> Mono.error(
                    new ValidationException("User already exists."))
                )
                .then(createAndSaveUser(user));
    }

    @Override
    public String login(User user) {
        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken
                            .unauthenticated(user.getName(), user.getPassword());

        authenticationManager.authenticate(token);

        return jwtService.generateToken(user.getName());
    }

    private Mono<User> createAndSaveUser(User user) {
        user.setUuid(UUID.randomUUID());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}