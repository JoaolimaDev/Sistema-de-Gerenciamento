package com.essia.desafio_essia.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.essia.desafio_essia.config.ExceptionHandler.CustomException;
import com.essia.desafio_essia.domain.model.neo4j.FileNode;
import com.essia.desafio_essia.domain.repository.neo4j.FileNodeRepository;
import com.essia.desafio_essia.dto.FileNodePostRequest;
import com.essia.desafio_essia.service.FileNodeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional("neo4jTransaction")
public class FileNodeServiceImpl implements FileNodeService {

    public final FileNodeRepository fileNodeRepository;

    @Override
    public FileNode createFilenode(FileNodePostRequest fileNode){

        Optional<FileNode> fileOptional = fileNodeRepository.findByname(fileNode.name());

        if (fileOptional.isPresent()) {
            throw new CustomException("O nome fornecido já está reservado: " + fileOptional.get().getName(), HttpStatus.BAD_REQUEST);
        }

        FileNode newNode = FileNode.builder().name(fileNode.name())
        .isDirectory(fileNode.isDirectory()).build();

        if (fileNode.parentNode() != null) {

            newNode.setIsChild(true);

            Optional<FileNode> parentfileOptional = fileNodeRepository.findByname(fileNode.parentNode());
            FileNode fatherNode = parentfileOptional.filter(FileNode::getIsDirectory)
            .orElseThrow(() -> new CustomException("Parent node inválido!", HttpStatus.BAD_REQUEST));

            fatherNode.addChild(newNode);

            return fileNodeRepository.save(fatherNode);
            
        }
        
        newNode.setIsChild(false);

       return fileNodeRepository.save(newNode);

    }

    @Override
    public Page<FileNode>getAllFileNode(int pageNumber, int pageSize) {
       
        Page<FileNode> fileNodes = fileNodeRepository.findAll(PageRequest.of(pageNumber, pageSize));

        long totalElements = fileNodes.getNumberOfElements();

        List<FileNode> pagedNodes = fileNodes.stream()
        .filter(predicate -> !predicate.getIsChild())
        .collect(Collectors.toList());

        return new PageImpl<>(pagedNodes, PageRequest.of(pageNumber, pageSize), totalElements);    
    }

    @Override
    public FileNode getFileNodeById(Long Id) {
        
        return fileNodeRepository.findById(Id)
        .orElseThrow(() -> new CustomException("Nenhum arquivo ou diretório encontrado para o id enviado!",
        HttpStatus.BAD_REQUEST));
    }

    @Override
    public FileNode getFileNodeByName(String name) {
        
        return fileNodeRepository.findByname(name)
        .orElseThrow(() -> new CustomException("Nenhum arquivo ou diretório encontrado para"+
        "o nome enviado!", HttpStatus.BAD_REQUEST));
    }

    @Override
    public FileNode updateFileNode() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateFileNode'");
    }

    @Override
    public Void deleteFileNodeById(Long Id) {

        fileNodeRepository.deleteById(Id);
        return null;
    }



}
