package com.example.split_eazy.authentication.service;

import com.example.split_eazy.user.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthUserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByName(username)
                .map(user -> User.builder()
                                .username(user.getName())
                                .password(user.getPassword())
                                .build()
                ).switchIfEmpty(
                        Mono.error(
                                new UsernameNotFoundException(
                                        "User with username [%s] not found".formatted(username)
                                )
                        )
                ).block();
    }
}