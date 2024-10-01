package com.essia.desafio_essia.service;


import org.springframework.data.domain.Page;

import com.essia.desafio_essia.domain.model.neo4j.FileNode;
import com.essia.desafio_essia.dto.FileNodePostRequest;
import com.essia.desafio_essia.dto.FileNodePutRequest;

public interface FileNodeService {
    public FileNode createFilenode(FileNodePostRequest fileNode);
    public Page<FileNode> getAllFileNode(int pageNumber, int pageSize);
    public FileNode getFileNodeById(Long Id);
    public FileNode getFileNodeByName(String name);
    public FileNode updateFileNode(FileNodePutRequest fileNodePutRequest, String name);
    public void deleteFileNodeById(Long Id);
}
