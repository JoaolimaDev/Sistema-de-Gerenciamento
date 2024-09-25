package com.essia.desafio_essia.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record FileNodePostRequest(

    @NotBlank @NotEmpty(message="Por favor preencha o nome do arquivo ou diretório!") String name,    
    @NotBlank @NotEmpty(message="Por favor preencha true para diretórios e false para arquivos!") Boolean isDirectory,
    String parentNode
) {}
