package com.essia.desafio_essia.domain.repository.neo4j;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import com.essia.desafio_essia.domain.model.neo4j.FileNode;

public interface FileNodeRepository extends Neo4jRepository<FileNode, Long> {

    
}