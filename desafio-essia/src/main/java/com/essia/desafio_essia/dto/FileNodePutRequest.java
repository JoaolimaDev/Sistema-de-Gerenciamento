package com.essia.desafio_essia.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record FileNodePutRequest(

    @NotBlank @NotEmpty(message="Por favor preencha o novo nome do arquivo ou diretório!")  String newName,    
    @NotBlank @NotEmpty(message="Por favor preencha true para diretórios e false para arquivos!") Boolean isDirectory,
    String parentNode
) {}
