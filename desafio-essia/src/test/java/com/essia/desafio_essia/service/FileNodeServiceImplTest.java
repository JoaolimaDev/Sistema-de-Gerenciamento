package com.essia.desafio_essia.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import com.essia.desafio_essia.config.ExceptionHandler.CustomException;
import com.essia.desafio_essia.domain.model.neo4j.FileNode;
import com.essia.desafio_essia.domain.repository.neo4j.FileNodeRepository;
import com.essia.desafio_essia.dto.FileNodePostRequest;
import com.essia.desafio_essia.dto.FileNodePutRequest;
import com.essia.desafio_essia.service.impl.FileNodeServiceImpl;

public class FileNodeServiceImplTest {

    @Mock
    private FileNodeRepository fileNodeRepository;

    @InjectMocks
    private FileNodeServiceImpl fileNodeService;
    

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Order(1)
    void createFilenodeTest(){

        FileNodePostRequest request = new FileNodePostRequest("root", true, null);
        when(fileNodeRepository.findByname("root")).thenReturn(Optional.empty());
        when(fileNodeRepository.save(any(FileNode.class))).thenAnswer(invocation -> invocation.getArgument(0));

        FileNode newNode = fileNodeService.createFilenode(request);

        assertEquals("root", newNode.getName());
        verify(fileNodeRepository).save(any(FileNode.class));

    }   

    @Test
    @Order(2)
    void createFilenodeTestFailed(){

        FileNode fileNode = new FileNode();
        fileNode.setName("root");

        FileNodePostRequest request = new FileNodePostRequest("root", false, null);
        when(fileNodeRepository.findByname("root")).thenReturn(Optional.of(fileNode));

        CustomException thrown = assertThrows(CustomException.class, () -> {
            fileNodeService.createFilenode(request);
        });

        assertEquals("O nome fornecido já está reservado: root", thrown.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatus());

    }

    @Test
    @Order(3)
    void getAllFileNodeTest(){

        int pageNumber = 0;
        int pageSize = 10;
        long skip = (long) pageNumber * pageSize;

        FileNode fileNode1 = FileNode.builder().name("root").isChild(false)
        .isDirectory(true).build();
        FileNode fileNode2 =  FileNode.builder().name("var").isChild(false)
        .isDirectory(true).build();
        List<FileNode> fileNodes = Arrays.asList(fileNode1, fileNode2);

        when(fileNodeRepository.findNodesPagination(skip, pageSize)).thenReturn(fileNodes);

        Page<FileNode> result = fileNodeService.getAllFileNode(pageNumber, pageSize);


        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals("root", result.getContent().get(0).getName());
        assertEquals("var", result.getContent().get(1).getName());
        verify(fileNodeRepository, times(1)).findNodesPagination(skip, pageSize);
    }

    @Test
    @Order(4)
    void getFileNodeByIdTest(){
        Long id = 1L;
        FileNode newNode = FileNode.builder().name("root").isChild(false)
        .isDirectory(true).Id(id).build();
        when(fileNodeRepository.findById(id)).thenReturn(Optional.of(newNode));

        FileNode result = fileNodeService.getFileNodeById(id);

        assertNotNull(result);
        assertEquals("root", result.getName());
        verify(fileNodeRepository, times(1)).findById(id);
    }

    @Test
    @Order(5)
    void getFileNodeByIdTestFiled(){

        Long id = 1L;
        when(fileNodeRepository.findById(id)).thenReturn(Optional.empty());


        CustomException thrown = assertThrows(CustomException.class, () -> {
            fileNodeService.getFileNodeById(id);
        });


        assertEquals("Nenhum arquivo ou diretório encontrado para o id enviado!", thrown.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatus());
        verify(fileNodeRepository, times(1)).findById(id);

    }
    @Test
    @Order(6)
    void getFileNodeByNameTest(){

        String name = "root";
        FileNode newNode = new FileNode();
        newNode.setName(name);
        when(fileNodeRepository.findByname(name)).thenReturn(Optional.of(newNode));

        FileNode result = fileNodeService.getFileNodeByName(name);

        assertNotNull(result);
        assertEquals(name, result.getName());
        verify(fileNodeRepository, times(1)).findByname(name);
    }

    @Test
    @Order(7)
    void getFileNodeByNameTestFailed(){

        String name = "root";
        when(fileNodeRepository.findByname(name)).thenReturn(Optional.empty());

        CustomException thrown = assertThrows(CustomException.class, () -> {
            fileNodeService.getFileNodeByName(name);
        });

        assertEquals("Nenhum arquivo ou diretório encontrado parao nome enviado!", thrown.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatus());
        verify(fileNodeRepository, times(1)).findByname(name);
    }

    @Test
    @Order(8)
    void updateFileNodeTest() {
        String newName = "rootAtualizado";
        FileNode fileNode = new FileNode();
        fileNode.setName("root");
        fileNode.setIsDirectory(true);
        FileNodePutRequest putRequest = new FileNodePutRequest(newName, true, null);
    
    
        when(fileNodeRepository.findByname(fileNode.getName())).thenReturn(Optional.of(fileNode));
        when(fileNodeRepository.findByname(newName)).thenReturn(Optional.empty());
        when(fileNodeRepository.save(any(FileNode.class))).thenAnswer(invocation -> invocation.getArgument(0));
    
        FileNode updatedNode = fileNodeService.updateFileNode(putRequest, "root");
    
 
        assertNotNull(updatedNode);
        assertEquals(newName, updatedNode.getName());
        verify(fileNodeRepository, times(1)).findByname("root");
        verify(fileNodeRepository, times(2)).findByname(newName); 
        verify(fileNodeRepository, times(1)).save(fileNode); 
    }
    
    @Test
    @Order(9)
    void updateFileNodeTestFailed() {
        String currentName = "root";
        String newName = "rootAtualiado";
        FileNode fileNode = new FileNode();
        fileNode.setName("root");
        FileNode existingFileNode = new FileNode();
        existingFileNode.setName(newName);
        FileNodePutRequest putRequest = new FileNodePutRequest(newName, false, null);
    
   
        when(fileNodeRepository.findByname(currentName)).thenReturn(Optional.of(fileNode));
        when(fileNodeRepository.findByname(newName)).thenReturn(Optional.of(existingFileNode)); 
    
     
        CustomException thrown = assertThrows(CustomException.class, () -> {
            fileNodeService.updateFileNode(putRequest, currentName);
        });
   
        assertEquals("O nome fornecido já está reservado: " + existingFileNode.getName(), thrown.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatus());
        verify(fileNodeRepository, times(1)).findByname(currentName);
        verify(fileNodeRepository, times(1)).findByname(newName);
    }
    


    @Test
    @Order(10)
    void updateFileNodeTestFailedParentNode(){

        String currentName = "CurrentNode";
        String newName = "UpdatedNode";
        String parentName = "InvalidParentNode";
        FileNode fileNode = new FileNode();
        fileNode.setName(currentName);
        FileNodePutRequest putRequest = new FileNodePutRequest(newName, false, parentName);


        when(fileNodeRepository.findByname(currentName)).thenReturn(Optional.of(fileNode));
        when(fileNodeRepository.findByname(newName)).thenReturn(Optional.empty());
        when(fileNodeRepository.findByname(parentName)).thenReturn(Optional.empty());

    
        CustomException thrown = assertThrows(CustomException.class, () -> {
            fileNodeService.updateFileNode(putRequest, currentName);
        });

        assertEquals("Parent node inválido!", thrown.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatus());
        verify(fileNodeRepository, times(1)).findByname(currentName);
        verify(fileNodeRepository, times(2)).findByname(newName);
        verify(fileNodeRepository, times(1)).findByname(parentName);
    }

    @Test
    @Order(11)
    void deleteFileNodeById(){

        Long id = 1L;
        FileNode fileNode = new FileNode();
        fileNode.setId(id);

        when(fileNodeRepository.findById(id)).thenReturn(Optional.of(fileNode));

        
        fileNodeService.deleteFileNodeById(id);

        
        verify(fileNodeRepository, times(1)).findById(id);
        verify(fileNodeRepository, times(1)).delete(fileNode);
    }


    @Test
    @Order(12)
    void deleteFileNodeByIdTest(){

        Long id = 1L;

        when(fileNodeRepository.findById(id)).thenReturn(Optional.empty());

       
        CustomException thrown = assertThrows(CustomException.class, () -> {
            fileNodeService.deleteFileNodeById(id);
        });

        assertEquals("Nenhum arquivo ou diretório encontrado parao id enviado!", thrown.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatus());
        verify(fileNodeRepository, times(1)).findById(id);
        verify(fileNodeRepository, times(0)).delete(any(FileNode.class));
    }


    /***
     * Neste teste passamos o arquivo teste.txt como "filho" do diretório root, ou seja teste.txt pertence a root.
     ***/
    @Test
    @Order(13)
    void createFilenodeTestChildNode(){

        FileNode fileNode = new FileNode();
        fileNode.setName("root");
        fileNode.setIsDirectory(true);
    
        FileNodePostRequest request = new FileNodePostRequest("teste.txt", false, fileNode.getName());

        when(fileNodeRepository.findByname("teste.txt")).thenReturn(Optional.empty());
        when(fileNodeRepository.findByname("root")).thenReturn(Optional.of(fileNode)); 
        when(fileNodeRepository.save(any(FileNode.class))).thenAnswer(invocation -> invocation.getArgument(0));

        FileNode newNode = fileNodeService.createFilenode(request);

        assertEquals("root", newNode.getName());
        assertNotNull(newNode);
        assertTrue(newNode.getChildNode().stream().anyMatch(child -> child.getName().equals("teste.txt")));
        verify(fileNodeRepository, times(1)).findByname("teste.txt");
        verify(fileNodeRepository, times(1)).findByname("root");
        verify(fileNodeRepository, times(1)).save(fileNode);
    }
}   
