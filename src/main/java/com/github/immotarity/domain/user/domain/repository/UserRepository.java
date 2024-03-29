package com.github.immotarity.domain.user.domain.repository;

import com.github.immotarity.domain.user.domain.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    User save(User user);
}
