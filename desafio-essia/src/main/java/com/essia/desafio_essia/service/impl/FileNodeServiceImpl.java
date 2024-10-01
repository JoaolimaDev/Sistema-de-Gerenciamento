package com.essia.desafio_essia.service.impl;

import java.util.List;
import java.util.Optional;

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
import com.essia.desafio_essia.dto.FileNodePutRequest;
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
            throw new CustomException("O nome fornecido já está reservado: " + fileOptional.get().getName(),
            HttpStatus.BAD_REQUEST);
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
    public Page<FileNode> getAllFileNode(int pageNumber, int pageSize) {
       
        long skip = (long) pageNumber * pageSize;
        List<FileNode> fileNodes = fileNodeRepository.findNodesPagination(skip, pageSize);
        
        long total = fileNodeRepository.countFileNodes();
        
        return new PageImpl<>(fileNodes, PageRequest.of(pageNumber, pageSize), total);     
    }

    @Override
    public FileNode getFileNodeById(Long Id) {
        
        return fileNodeRepository.findById(Id)
        .orElseThrow(() -> new CustomException("Nenhum arquivo ou diretório encontrado para o id enviado!",
        HttpStatus.BAD_REQUEST));
    }

    @Override
    public Page<FileNode>  getFileNodeByName(String name, int pageNumber, int pageSize) {

        long skip = (long) pageNumber * pageSize;
        List<FileNode> fileNodes = fileNodeRepository.findNodesByNamePagination(name, skip, pageSize);
    
        long total = fileNodeRepository.countFileNodesByName(name);
    
        return new PageImpl<>(fileNodes, PageRequest.of(pageNumber, pageSize), total);    
    }

    @Override
    public FileNode updateFileNode(FileNodePutRequest fileNodePutRequest, String name) {

        FileNode fileNode = fileNodeRepository.findByname(name)
        .orElseThrow(() -> new CustomException("Nenhum arquivo ou diretório encontrado para"+
        "o nome enviado!", HttpStatus.BAD_REQUEST));

        Optional<FileNode> findOptional = fileNodeRepository.findByname(fileNodePutRequest.newName());

        if (findOptional.isPresent()) {
            
            throw new CustomException("O nome fornecido já está reservado: " + findOptional.get().getName(),
            HttpStatus.BAD_REQUEST);
        }

        fileNode.setName(fileNodePutRequest.newName());

        if (fileNodePutRequest.parentNode() != null) {
            
            Optional<FileNode> parentfileOptional = fileNodeRepository.findByname(fileNodePutRequest.parentNode());
            FileNode fatherNode = parentfileOptional.filter(FileNode::getIsDirectory)
            .orElseThrow(() -> new CustomException("Parent node inválido!", HttpStatus.BAD_REQUEST));

            fileNode.setIsChild(true);

            fatherNode.addChild(fileNode);
            return fileNodeRepository.save(fatherNode);
        }
        
        fileNode.setIsDirectory(fileNodePutRequest.isDirectory());
        return fileNodeRepository.save(fileNode);
    }

    @Override
    public void deleteFileNodeById(Long Id) {

        FileNode fileNode = fileNodeRepository.findById(Id).orElseThrow(
        () -> new CustomException("Nenhum arquivo ou diretório encontrado para"
        +"o id enviado!", HttpStatus.BAD_REQUEST));

        fileNodeRepository.delete(fileNode);
    }   



}
