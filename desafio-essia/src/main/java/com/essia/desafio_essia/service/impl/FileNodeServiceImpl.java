package com.essia.desafio_essia.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
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
    public Page<FileNode> getAllFileNode(int page, int size) {
       
        return fileNodeRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public FileNode getFileNodeById() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFileNodeById'");
    }

    @Override
    public FileNode getFileNodeByName() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getFileNodeByName'");
    }

    @Override
    public FileNode updateFileNode() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateFileNode'");
    }

    @Override
    public FileNode deleteFileNodeById() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteFileNodeById'");
    }



}
