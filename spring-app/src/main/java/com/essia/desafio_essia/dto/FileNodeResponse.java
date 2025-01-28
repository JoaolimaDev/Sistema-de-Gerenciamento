package com.essia.desafio_essia.dto;

import com.essia.desafio_essia.domain.model.neo4j.FileNode;

public record FileNodeResponse(

    FileNode fileNode,
    String status


) {
    
}
