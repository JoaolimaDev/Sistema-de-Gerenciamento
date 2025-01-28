package com.essia.desafio_essia.domain.model.neo4j;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.Relationship.Direction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Node
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileNode {

    @org.springframework.data.neo4j.core.schema.Id
    @GeneratedValue
    private Long Id;

    private String name;
    private Boolean isDirectory;
    private Boolean isChild;

    @Builder.Default
    @Relationship(type = "contains", direction = Direction.OUTGOING)
    private Set<FileNode> childNode = new HashSet<FileNode>();

    public void addChild(FileNode fileNode){
        this.childNode.add(fileNode);
    }
    
}
