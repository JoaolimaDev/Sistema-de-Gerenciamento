package com.essia.desafio_essia.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.essia.desafio_essia.dto.Response;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/filesystem")
public class FileSystemController {

    @Tag(name="Sistema de arquivos")
    @Operation(summary = "filesystem", description = "teste", 
    security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = ""),
        @ApiResponse(responseCode = "500", description = "")
    })
    @GetMapping("/")
    public ResponseEntity<Response> getFile(){

        Response response = new Response("Você chegou aqui!", HttpStatus.OK.name());
        return new ResponseEntity<>(response, HttpStatus.OK); 
    }
    
}
