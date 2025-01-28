package com.essia.desafio_essia.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.essia.desafio_essia.domain.model.h2.User;
import com.essia.desafio_essia.domain.repository.h2.UserRepository;

import jakarta.annotation.PostConstruct;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    void setUp() {
        userRepository.deleteAll();
        User user = new User();
        user.setUsername("admin_teste");
        user.setPassword(passwordEncoder.encode("admin123"));
        user.setRole("admin");
        userRepository.save(user);
    }

    @Test
    public void loginRequestTest() throws Exception {
        String userJson = """
            {
                "username": "admin_teste",
                "password": "admin123"
            }
        """;

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isOk());
    }
}
