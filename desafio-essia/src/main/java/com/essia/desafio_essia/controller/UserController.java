package com.essia.desafio_essia.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.essia.desafio_essia.config.utilities.JwtUtilities;
import com.essia.desafio_essia.domain.model.h2.User;
import com.essia.desafio_essia.domain.repository.h2.UserRepository;
import com.essia.desafio_essia.dto.LoginRequest;
import com.essia.desafio_essia.dto.Response;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtilities jwtUtil;

    @Tag(name="Autênticação")
    @Operation(summary = "Autentica o usuário e retorna o JWT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorno do token!",
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = LoginRequest.class)) }),  
        @ApiResponse(responseCode = "400", description = "Usuário ou senha inválidos!",
            content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Response.class)) })  
    })
    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody @Valid 
    @Schema(
        description = "Request body for user login",
        example = "{\"username\": \"admin\", \"password\": \"admin123\"}"
    )
    LoginRequest loginRequest){
        
        Optional<User> userOptional = userRepository.findByUsername(loginRequest.username());

        if (userOptional.isPresent()) {

            User user = userOptional.get();

            if (passwordEncoder.matches(loginRequest.password(), user.getPassword())) {

                String token = jwtUtil.generateToken(new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    AuthorityUtils.createAuthorityList(user.getRole())
                ));
            

                Response response = new Response(token, HttpStatus.OK.name());

                return new ResponseEntity<>(response, HttpStatus.OK);                
            }
            
        }

        Response response = new Response("Usuário ou senha inválidos!", HttpStatus.BAD_REQUEST.name());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);                
    }
}
