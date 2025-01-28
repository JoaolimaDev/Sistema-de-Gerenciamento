package com.essia.desafio_essia.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record LoginRequest(
        @NotNull @NotBlank(message = "Por favor preencha o nome do usuário!") String username,
        @NotNull @NotBlank(message = "Por favor preencha a senha!") String password) {
}
