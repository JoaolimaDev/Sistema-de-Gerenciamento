package com.essia.desafio_essia.controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.essia.desafio_essia.config.JwtUtilities;
import com.essia.desafio_essia.domain.model.User;
import com.essia.desafio_essia.domain.repository.UserRepository;
import com.essia.desafio_essia.dto.LoginRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtilities jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginRequest loginRequest){

        Optional<User> userOptional = userRepository.findByUsername(loginRequest.username());

        if (userOptional.isPresent()) {

            User user = userOptional.get();

            if (passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
                
                String token = jwtUtil.generateToken(new org.springframework.security.core.userdetails.User(
                    user.getUsername(), 
                    user.getPassword(), 
                    AuthorityUtils.createAuthorityList(user.getRole())
                ));

                return ResponseEntity.ok(token);
                
            }
            
        }

        return ResponseEntity.status(401).body("Usuário ou senha inválidos!");    
    }
}
