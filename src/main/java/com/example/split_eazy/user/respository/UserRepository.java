package com.example.split_eazy.user.respository;

import com.example.split_eazy.user.model.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserRepository extends ReactiveCrudRepository<User, Integer> { }