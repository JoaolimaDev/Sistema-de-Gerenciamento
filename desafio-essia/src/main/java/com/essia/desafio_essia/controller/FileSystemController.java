package com.essia.desafio_essia.controller;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.essia.desafio_essia.domain.model.neo4j.FileNode;
import com.essia.desafio_essia.dto.FileNodePostRequest;
import com.essia.desafio_essia.dto.FileNodePutRequest;
import com.essia.desafio_essia.dto.FileNodeResponse;
import com.essia.desafio_essia.dto.FileNodeResponseCreated;
import com.essia.desafio_essia.dto.Response;
import com.essia.desafio_essia.service.FileNodeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/filesystem")
@RequiredArgsConstructor
public class FileSystemController {

    public final FileNodeService fileNodeService;

    
    @Operation(summary = "filesystem", description = "teste", 
    security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "", 
        content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = FileNode.class)) }),
        @ApiResponse(responseCode = "500", description = "", 
        content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Response.class)) })
    })
    @GetMapping("/")
    public ResponseEntity<Page<FileNode>> getAllFileNode(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ){
        Page<FileNode> listFileNodes = fileNodeService.getAllFileNode(page, size);
        return new ResponseEntity<>(listFileNodes, HttpStatus.OK);
    }

    @Operation(summary = "filesystem", description = "teste", 
    security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = ""),
        @ApiResponse(responseCode = "500", description = "")
    })
    @PostMapping("/create")
    public ResponseEntity<FileNodeResponseCreated> createFile(
    @Valid
    @RequestBody
    @Schema(
        description = "Request body for user login",
        example = "{\"name\": \"root\", \"isDirectory\": true}"
    )
    FileNodePostRequest fileNode) {

        FileNode newFile = fileNodeService.createFilenode(fileNode);
        String uri = ServletUriComponentsBuilder.fromCurrentContextPath() 
        .path("/api/filesystem/getById")  
        .queryParam("id", newFile.getId())  
        .toUriString();  

        FileNodeResponseCreated fileNodeResponse = new FileNodeResponseCreated(newFile, HttpStatus.OK.name(), uri);


        return ResponseEntity.created(URI.create(uri)).body(fileNodeResponse);
    }

    @Operation(summary = "filesystem", description = "teste", 
    security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "", 
        content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = FileNode.class)) }),
        @ApiResponse(responseCode = "500", description = "", 
        content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = Response.class)) })
    })
    @GetMapping("/getById")
    public ResponseEntity<FileNodeResponse> getFileNodeById(
        @RequestParam Long id
    ){

        FileNode fileNode = fileNodeService.getFileNodeById(id);
        FileNodeResponse fileNodeResponse = new FileNodeResponse(fileNode, HttpStatus.OK.name());
        return new ResponseEntity<>(fileNodeResponse, HttpStatus.OK);
    }

    @GetMapping("/getByname")
    public ResponseEntity<FileNodeResponse> getFileNodeByName(
        @RequestParam String name
    ){

        FileNode fileNode = fileNodeService.getFileNodeByName(name);
        FileNodeResponse fileNodeResponse = new FileNodeResponse(fileNode, HttpStatus.OK.name());
        return new ResponseEntity<>(fileNodeResponse, HttpStatus.OK);
    }


    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteFileNodeById(
        @RequestParam Long id
    ){

        fileNodeService.deleteFileNodeById(id);
        return ResponseEntity.noContent().build();
       
    }


    @PutMapping("/update")
    public ResponseEntity<FileNodeResponse> updateFileNode(
        @RequestBody FileNodePutRequest fileNodePutRequest,
        @RequestParam String name
    ){

        FileNode fileNode = fileNodeService.updateFileNode(fileNodePutRequest, name);
        FileNodeResponse fileNodeResponse = new FileNodeResponse(fileNode, HttpStatus.OK.name());

        return new ResponseEntity<>(fileNodeResponse, HttpStatus.OK);
    }
    
}
