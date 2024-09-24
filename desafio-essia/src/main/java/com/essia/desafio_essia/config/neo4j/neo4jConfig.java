package com.essia.desafio_essia.config.neo4j;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.springframework.boot.autoconfigure.data.neo4j.Neo4jDataAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.core.transaction.Neo4jTransactionManager;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@Configuration
@EnableNeo4jRepositories(basePackages= "com.essia.desafio_essia.domain.repository.neo4j", 
transactionManagerRef="neo4jTransaction"
)
public class neo4jConfig extends Neo4jDataAutoConfiguration {
    
    @Bean
    public Driver getDriver(){
        return GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "password"));
    }

    @Bean(name = "neo4jTransaction")
    public Neo4jTransactionManager neo4jTransactionManager(){
        return new Neo4jTransactionManager(getDriver());
    }
}   
