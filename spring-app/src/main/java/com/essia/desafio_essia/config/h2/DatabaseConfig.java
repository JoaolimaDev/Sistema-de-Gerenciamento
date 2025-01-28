package com.essia.desafio_essia.config.h2;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.essia.desafio_essia.domain.model.h2.User;
import com.essia.desafio_essia.domain.repository.h2.UserRepository;

import jakarta.annotation.PostConstruct;

@Component
public class DatabaseConfig {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        if (userRepository.findByUsername("admin").isEmpty() || userRepository.findByUsername("user").isEmpty()) {
            
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ADMIN");

            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setRole("USER");

            
            userRepository.saveAll(Arrays.asList(admin, user));

        }
    }
}
