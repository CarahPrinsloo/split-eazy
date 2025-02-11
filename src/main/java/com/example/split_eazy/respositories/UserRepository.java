package com.example.split_eazy.respositories;

import com.example.split_eazy.models.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserRepository extends ReactiveCrudRepository<User, Integer> { }