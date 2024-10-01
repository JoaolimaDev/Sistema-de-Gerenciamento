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
import com.essia.desafio_essia.service.FileNodeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/filesystem")
@RequiredArgsConstructor
public class FileSystemController {

    public final FileNodeService fileNodeService;

    
    @Operation(summary = "Retorna todos arquivos paginados!", 
    description = "Enviando a página e tamanho do output por página (request param page e size)"+
    " as entidades serão retornadas utilizando a interface Page", 
    security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorno das entidades!", 
        content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = FileNode.class)) })
    })
    @GetMapping("/")
    public ResponseEntity<Page<FileNode>> getAllFileNode(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ){
        Page<FileNode> listFileNodes = fileNodeService.getAllFileNode(page, size);
        return new ResponseEntity<>(listFileNodes, HttpStatus.OK);
    }

    @Operation(summary = "Criação de entidades", 
    description = "Neste endpoint temos o exemplo da criação de uma entidade do tipo arquivo, de nome teste.txt"
    + " entidades do tipo arquivo recebem a propriedade isDirectory como false ", 
    security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorno da entidade!")
    })
    @PostMapping("/createExample2")
    public ResponseEntity<FileNodeResponseCreated> createFileExample2(
    @RequestBody
    @Schema(
        description = "Request body",
        example = "{\"name\": \"teste.txt\", \"isDirectory\": false}"
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


    @Operation(summary = "Criação de entidades", 
    description = "Neste endpoint temos o exemplo da criação de uma entidade do tipo arquivo, de nome teste02.txt"
    + " entidades do tipo arquivo recebem a propriedade isDirectory como false, neste exemplo a entidade será filha"
    +" do diretório root caso este já tenha sido criado, isso significa que teste02.txt está contido na pasta root.", 
    security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorno da entidade!")
    })
    @PostMapping("/createExample3")
    public ResponseEntity<FileNodeResponseCreated> createFileExample3(
    @RequestBody
    @Schema(
        description = "Request body",
        example = "{\"name\": \"teste02.txt\", \"isDirectory\": false, \"parentNode\": \"root\" }"
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


    @Operation(summary = "Criação de entidades", 
    description = "Neste endpoint temos o exemplo da criação de uma entidade do tipo diretório, de nome root"
    + " entidades do tipo diretório recebem a propriedade isDirectory como TRUE ", 
    security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorno da entidade!")
    })
    @PostMapping("/create")
    public ResponseEntity<FileNodeResponseCreated> createFile(
    @RequestBody
    @Schema(
        description = "Request body",
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

    @Operation(summary = "Retorna a entidade por ID!", 
    description = "Neste endpoint enviando a query param ID do tipo long, retornamos a entidade.", 
    security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorno da entidade!", 
        content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = FileNode.class))})
    })
    @GetMapping("/getById")
    public ResponseEntity<FileNodeResponse> getFileNodeById(
        @RequestParam Long id
    ){

        FileNode fileNode = fileNodeService.getFileNodeById(id);
        FileNodeResponse fileNodeResponse = new FileNodeResponse(fileNode, HttpStatus.OK.name());
        return new ResponseEntity<>(fileNodeResponse, HttpStatus.OK);
    }


    @Operation(summary = "Retorna a entidade por nome!", 
    description = "Neste endpoint enviando a query param name do tipo String, retornamos a entidade.", 
    security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorno da entidade!", 
        content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = FileNode.class))})
    })
    @GetMapping("/getByname")
    public ResponseEntity<Page<FileNode>> getFileNodeByName(
        @RequestParam String name,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size

    ){

        Page<FileNode> fileNode = fileNodeService.getFileNodeByName(name, page, size);
        return new ResponseEntity<>(fileNode, HttpStatus.OK);
    }


    @Operation(summary = "Deleta a entidade por id!", 
    description = "Neste endpoint enviando a query param id do tipo long, deleta a entidade.", 
    security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sem retorno!", 
        content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = FileNode.class))})
    })
    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteFileNodeById(
        @RequestParam Long id
    ){

        fileNodeService.deleteFileNodeById(id);
        return ResponseEntity.noContent().build();
       
    }


    @Operation(summary = "Atualiza a entidade!", 
    description = "Neste endpoint enviando a query param name do tipo String, junto coma requisição enviando, um body"
    +" do tipo json, com as  caractéristicas a serem afetadas atualizamos a entidade. Neste exemplo caso já tenha sido"
    +" criado atualizaremos o nome da entidade 'root' para 'rootAtualizado'", 
    security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Sem retorno!", 
        content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = FileNode.class))})
    })
    @PutMapping("/update")
    public ResponseEntity<FileNodeResponse> updateFileNode(
        @RequestBody
        @Schema(
            description = "Request body",
            example = "{\"newName\": \"rootAtualizado\", \"isDirectory\": true}"
        )
        FileNodePutRequest fileNodePutRequest,
        @RequestParam String name
    ){

        FileNode fileNode = fileNodeService.updateFileNode(fileNodePutRequest, name);
        FileNodeResponse fileNodeResponse = new FileNodeResponse(fileNode, HttpStatus.OK.name());

        return new ResponseEntity<>(fileNodeResponse, HttpStatus.OK);
    }
    
}
