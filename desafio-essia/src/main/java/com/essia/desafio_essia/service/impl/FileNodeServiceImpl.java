package com.essia.desafio_essia.service.impl;

import java.util.List;
import java.util.Optional;

import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.essia.desafio_essia.domain.model.neo4j.FileNode;
import com.essia.desafio_essia.domain.repository.neo4j.FileNodeRepository;
import com.essia.desafio_essia.dto.FileNodeRequest;
import com.essia.desafio_essia.service.FileNodeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional("neo4jTransaction")
public class FileNodeServiceImpl implements FileNodeService {

    public final FileNodeRepository fileNodeRepository;

    @Override
    public FileNode createFilenode(FileNodeRequest fileNode) throws BadRequestException {

        Optional<FileNode> fileNameOptional = fileNodeRepository.findByname(fileNode.name());

        if (fileNameOptional.isPresent()) {
            throw new BadRequestException("O nome enviado já está reservado!");
        }

        FileNode newNode = FileNode.builder().name(fileNode.name())
        .isDirectory(fileNode.isDirectory()).build();

        if (fileNode.parentNode() != null) {

            Optional<FileNode> parentfileOptional = fileNodeRepository.findByname(fileNode.parentNode());
            FileNode fatherNode = parentfileOptional
            .orElseThrow(() -> new BadRequestException("O nó pai não foi encontrado!"));

            if (fatherNode.getIsDirectory()) {

                fatherNode.addChild(newNode);

                return fileNodeRepository.save(fatherNode);
            }

            throw new BadRequestException("Os nó do tipo Pai devem ser diretórios!");
            
        }

       return fileNodeRepository.save(newNode);

    }

    @Override
    public List<FileNode> getAllFileNode() {
        return fileNodeRepository.findAll();
    }


}
