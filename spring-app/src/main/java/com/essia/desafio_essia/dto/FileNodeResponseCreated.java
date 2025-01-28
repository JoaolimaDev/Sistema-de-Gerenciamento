package com.essia.desafio_essia.dto;

import com.essia.desafio_essia.domain.model.neo4j.FileNode;

public record FileNodeResponseCreated( FileNode fileNode,
String status,
String uri) {
}
