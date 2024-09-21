package com.essia.desafio_essia.dto;

import jakarta.validation.constraints.NotBlank;


public record LoginRequest(
        @NotBlank(message = "Por favor preencha o nome do usuário!") String username,
        @NotBlank(message = "POr favor preencha a senha!") String password) {
}
