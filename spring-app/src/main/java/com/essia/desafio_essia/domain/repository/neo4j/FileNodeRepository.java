package com.essia.desafio_essia.domain.repository.neo4j;

import java.util.List;
import java.util.Optional;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.essia.desafio_essia.domain.model.neo4j.FileNode;
@Repository
public interface FileNodeRepository extends Neo4jRepository<FileNode, Long> {

    Optional<FileNode> findByname(String name);

    @Query("""
    MATCH (node:FileNode)
    WHERE node.isChild = false
    OPTIONAL MATCH (node)-[r:contains]->(child:FileNode)
    RETURN node, collect(r), collect(child)
    SKIP $skip LIMIT $limit
    """)
    List<FileNode> findNodesPagination(@Param("skip") long skip, @Param("limit") int limit);
    
    @Query("""
    MATCH (node:FileNode)
    WHERE node.isChild = false
    RETURN count(node)
    """)
    long countFileNodes();

}