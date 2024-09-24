package com.essia.desafio_essia.domain.repository.h2;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.essia.desafio_essia.domain.model.h2.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
