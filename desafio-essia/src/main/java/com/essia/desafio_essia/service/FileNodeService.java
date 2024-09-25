package com.essia.desafio_essia.service;


import java.util.List;

import org.apache.coyote.BadRequestException;

import com.essia.desafio_essia.domain.model.neo4j.FileNode;
import com.essia.desafio_essia.dto.FileNodeRequest;

public interface FileNodeService {
    public FileNode createFilenode(FileNodeRequest fileNode) throws BadRequestException;
    public List<FileNode>  getAllFileNode();
}
