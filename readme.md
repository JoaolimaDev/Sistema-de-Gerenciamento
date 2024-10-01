## Desafio Essia, sistema de gerenciamento de arquivos -- Dev João vitor de lima Desenvolvedor Fullstark Pleno

Restful api, com frontend completo, autênticação e validação via JWT token

## Principais Tecnologias

- **Java 17**: java version 17.0.6-tem
- **Spring Boot 3**: 3.3.4
- **Spring Data JPA**: versão mais recente
- **Spring Data Neo4j**: versão mais recente
 - **OpenAPI (Swagger)**: 2.6.0

## Diagrama de Classes (Domínio da API)
```mermaid
classDiagram
    class FileNodeServiceImpl {
        +FileNode createFilenode(FileNodePostRequest)
        +Page<FileNode> getAllFileNode(int, int)
        +FileNode getFileNodeById(Long)
        +FileNode getFileNodeByName(String)
        +FileNode updateFileNode(FileNodePutRequest, String)
        +void deleteFileNodeById(Long)
    }

    class FileNodeRepository {
        +Optional<FileNode> findByname(String)
        +List<FileNode> findNodesPagination(long, int)
        +long countFileNodes()
        +Optional<FileNode> findById(Long)
        +void delete(FileNode)
        +FileNode save(FileNode)
    }

    class FileNode {
        +String name
        +boolean isDirectory
        +boolean isChild
        +void addChild(FileNode)
    }

    class CustomException {
        +String message
        +HttpStatus status
    }

    FileNodeServiceImpl --> FileNodeRepository : uses
    FileNodeServiceImpl --> FileNode : manages
    FileNodeServiceImpl --> CustomException : throws
    FileNodeRepository --> FileNode : returns
```


## API Endpoints
-------------

| Método | Endpoint                                   | Descrição                                   |
|--------|--------------------------------------------|---------------------------------------------|
| POST   | `/api/filesystem/create`                     | Cria um novo arquivo ou diretório           |
| GET    | `/api/filesystem/?page={page}&size={size}`   | Recupera uma lista paginada de file nodes   |
| GET    | `api/filesystem/{id}`                        | Obtém um arquivo ou diretório pelo ID       |
| GET    | `/api/filesystem/{name}`                     | Obtém um arquivo ou diretório pelo nome     |
| PUT    | `/api/filesystem/update?name={name}'`        | Atualiza um arquivo ou diretório existente  |
| DELETE | `/api/filesystem/delete?id={id}`             | Deleta um arquivo ou diretório pelo ID      |
| POST   | `/api/auth/login`                            | Autentica um usuário                        |

## Diagrama cypher no NEO4J, exemplo de uma estrutura unix like, da pasta VAR -> WWW -> INDEX.HTML, AONDE OS RELACIONAMENTOS SÃO MARCADOS POR CONTAINS, SENDO QUE ROOT CONTAINS VAR, VAR CONTAINS WWW E WWW CONTAINS INDEX.HTML
![Alt text](neo4j.png)

O uso do Neo4j em um sistema de gerenciamento de arquivos é justificado por sua capacidade de modelar relacionamentos complexos de forma eficiente. Arquivos e diretórios, que possuem hierarquias naturais, são facilmente representados como grafos, permitindo consultas rápidas sobre a estrutura do sistema.

Neo4j garante a integridade dos dados por meio de transações em operações de criação, atualização e exclusão.
A escalabilidade do Neo4j é um fator importante, permitindo que o sistema cresça em volume e complexidade sem perda de desempenho.