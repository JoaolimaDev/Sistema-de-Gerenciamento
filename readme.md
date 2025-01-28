# Sistema de Gerenciamento de Arquivos

**Desenvolvedor:** João Vitor de Lima  
**Cargo:** Desenvolvedor Fullstack Pleno

Bem-vindo ao **Sistema de Gerenciamento de Arquivos**, uma solução robusta e escalável para gerenciar arquivos e diretórios de forma eficiente. Este projeto combina uma API Restful com um frontend completo, integrando autenticação segura via JWT e validações robustas. Com uma arquitetura baseada em grafos utilizando Neo4j, o sistema é capaz de modelar hierarquias complexas de arquivos e diretórios, garantindo desempenho e integridade dos dados.

---

## 🚀 Principais Tecnologias

- **Java 17**  
- **Spring Boot 3**  
- **Spring Data JPA**  
- **Spring Data Neo4j**  
- **OpenAPI (Swagger)**  
- **H2 Database**  

---

## 🚨 Aviso

Certifique-se de que as seguintes portas estão disponíveis:  
- **8080** (Backend)  
- **3000** (Frontend)  
- **7687** (Neo4j Bolt)  
- **7474** (Neo4j Browser)  

---

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
| GET    | `api/filesystem/?id={id}`                    | Obtém um arquivo ou diretório pelo ID       |
| GET    | `/api/filesystem/?name={name}`               | Obtém um arquivo ou diretório pelo nome     |
| PUT    | `/api/filesystem/update?name={name}'`        | Atualiza um arquivo ou diretório existente  |
| DELETE | `/api/filesystem/delete?id={id}`             | Deleta um arquivo ou diretório pelo ID      |
| POST   | `/api/auth/login`                            | Autentica um usuário                        |

## Diagrama cypher no NEO4J, exemplo de uma estrutura unix like, da pasta VAR -> WWW -> INDEX.HTML, AONDE OS RELACIONAMENTOS SÃO MARCADOS POR CONTAINS, SENDO QUE ROOT CONTAINS VAR, VAR CONTAINS WWW E WWW CONTAINS INDEX.HTML
![Alt text](neo4j.png)

O uso do Neo4j em um sistema de gerenciamento de arquivos é justificado por sua capacidade de modelar relacionamentos complexos de forma eficiente. Arquivos e diretórios, que possuem hierarquias naturais, são facilmente representados como grafos, permitindo consultas rápidas sobre a estrutura do sistema.

Neo4j garante a integridade dos dados por meio de transações em operações de criação, atualização e exclusão.
A escalabilidade do Neo4j é um fator importante, permitindo que o sistema cresça em volume e complexidade sem perda de desempenho.

## Como utilizar

```bash
    git clone https://github.com/JoaolimaDev/desafio-essia.git
    cd desafio-essia
    cd desafio-essia
    mvn clean package
    cd ..
    sudo docker-compose up --build
```

🚨 **Aviso:** Foi implementada autenticação JWT, utilizando Spring Security. Dois usuários estão disponíveis, além de testes unitários na camada de service e de integração na camada de controller.

1. **Usuário Admin**
   - **Nome de Usuário:** admin
   - **Senha:** admin123

2. **Usuário Comum**
   - **Nome de Usuário:** user
   - **Senha:** user123


1. **SWAGGER DISPONÍVEL**
   - **URL:** http://localhost:8080/swagger-ui/index.html

2. **ACESSO AO CONSOLE DO BANCO NEO4J**
   - **URL:** http://localhost:7474/browser/
  
2. **Front-end**
 - **URL:**  http://localhost:3000


A ideia era implementar o conceito de RBAC (role-based access control), que ficará para uma próxima atualização do projeto.

<p align="left">
  💌 Contatos: ⤵️
</p>

<p align="left">
  <a href="mailto:ozymandiasphp@gmail.com" title="Gmail">
  <img src="https://img.shields.io/badge/-Gmail-FF0000?style=flat-square&labelColor=FF0000&logo=gmail&logoColor=white&link=LINK-DO-SEU-GMAIL" alt="Gmail"/></a>
  <a href="https://www.linkedin.com/in/jo%C3%A3o-vitor-de-lima-74441b1b1/" title="LinkedIn">
  <img src="https://img.shields.io/badge/-Linkedin-0e76a8?style=flat-square&logo=Linkedin&logoColor=white&link=LINK-DO-SEU-LINKEDIN" alt="LinkedIn"/></a>
  <a href="https://wa.me/5581989553431" title="WhatsApp">
  <img src="https://img.shields.io/badge/-WhatsApp-25d366?style=flat-square&labelColor=25d366&logo=whatsapp&logoColor=white&link=API-DO-SEU-WHATSAPP" alt="WhatsApp"/></a>
</p>




   
