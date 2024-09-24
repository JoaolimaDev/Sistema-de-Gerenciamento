package com.essia.desafio_essia.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.essia.desafio_essia.domain.model.neo4j.FileNode;
import com.essia.desafio_essia.domain.repository.neo4j.FileNodeRepository;
import com.essia.desafio_essia.service.FileNodeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional("neo4jTransaction")
public class FileNodeServiceImpl implements FileNodeService {

    public final FileNodeRepository fileNodeRepository;

    @Override
    public FileNode createFilenode(FileNode fileNode) {
        
       return fileNodeRepository.save(fileNode);

    }


    
}
