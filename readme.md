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