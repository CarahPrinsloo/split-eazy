package com.example.split_eazy.user.respository;

import com.example.split_eazy.user.model.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Integer> {
    Mono<User> findByEmailAddress(String emailAddress);

    Mono<User> findByName(String name);
}