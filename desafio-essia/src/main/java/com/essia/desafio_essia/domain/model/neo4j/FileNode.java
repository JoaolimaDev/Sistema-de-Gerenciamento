package com.essia.desafio_essia.domain.model.neo4j;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Node
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileNode {

    @org.springframework.data.neo4j.core.schema.Id
    @GeneratedValue
    private Long Id;

    private String name;
    
}
