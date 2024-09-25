package com.essia.desafio_essia.service;


import org.springframework.data.domain.Page;

import com.essia.desafio_essia.domain.model.neo4j.FileNode;
import com.essia.desafio_essia.dto.FileNodePostRequest;

public interface FileNodeService {
    public FileNode createFilenode(FileNodePostRequest fileNode);
    public Page<FileNode> getAllFileNode(int page, int size);
    public FileNode getFileNodeById();
    public FileNode getFileNodeByName();
    public FileNode updateFileNode();
    public FileNode deleteFileNodeById();
}
