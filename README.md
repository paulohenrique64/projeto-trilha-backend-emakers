## Projeto Trilha Backend Emakers

Este projeto consiste na implementação de uma API REST para gerenciamento de usuários, livros e empréstimos de livros. Abaixo, se encontra o diagrama do banco de dados.

![](/docs/img.png)

### Principais tecnologias e frameworks utilizados

- Spring e Spring Boot
- Spring Security
- FlyWay
- Hibernate
- Banco de dados MySQL

### Como rodar a aplicação localmente

Criando e executando o banco de dados utilizando a ferramenta Docker:
``` shell
docker run --name mysql-rota-backend-emakers -e MYSQL_ROOT_PASSWORD=admin123 -p 3306:3306 -d mysql:8.1
```

Com o banco de dados em execução, criamos as tabelas a partir dos arquivos de migração utilizando o comando abaixo:
``` shell
./mvnw flyway:migrate 
```

Para rodar a aplicação java, é recomendado utilizar a JDK 17 ou superior, com isso será possível executar a aplicação com o comando abaixo:
``` shell
./mvnw spring-boot:run 
```
🚀 Com a aplicação em execução, será possível acessar a documentação pela url: [http://localhost:8080/swagger-ui](http://localhost:8080/swagger-ui)

### Usuário administrador

Usuários administradores (que possuem a role `ROLE_ADMIN`), podem ser criados alterando o campo `role_id`, da tabela `users`, diretamente no banco de dados, para o valor 1.

### Estrutura geral do projeto
```
├── docs
├── flyway.conf
├── mvnw
├── mvnw.cmd
├── pom.xml
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── paulohenrique
│   │   │           └── library
│   │   │               ├── controller
│   │   │               │   ├── AuthController.java
│   │   │               │   ├── BookController.java
│   │   │               │   ├── LoanController.java
│   │   │               │   ├── RestControllerExceptionHandler.java
│   │   │               │   └── UserController.java
│   │   │               ├── data
│   │   │               │   ├── dto
│   │   │               │   │   ├── request
│   │   │               │   │   │   ├── AccountDeleteRequestDto.java
│   │   │               │   │   │   ├── BookEditRequestDto.java
│   │   │               │   │   │   ├── BookRequestDto.java
│   │   │               │   │   │   ├── LoginRequest.java
│   │   │               │   │   │   ├── RegisterRequest.java
│   │   │               │   │   │   └── UpdateUserRequestDto.java
│   │   │               │   │   └── response
│   │   │               │   │       ├── GeneralResponseDto.java
│   │   │               │   │       ├── LoginResponseDto.java
│   │   │               │   │       ├── RegisterResponseDto.java
│   │   │               │   │       └── UserResponseDto.java
│   │   │               │   └── entity
│   │   │               │       ├── Book.java
│   │   │               │       ├── Loan.java
│   │   │               │       ├── Role.java
│   │   │               │       └── User.java
│   │   │               ├── exception
│   │   │               │   ├── LibraryApiException.java
│   │   │               │   └── UnauthorizedException.java
│   │   │               ├── infra
│   │   │               │   ├── CorsConfig.java
│   │   │               │   ├── CustomAccessDeniedHandler.java
│   │   │               │   ├── OpenApiConfig.java
│   │   │               │   ├── SecurityConfig.java
│   │   │               │   └── ValidateTokenFilter.java
│   │   │               ├── repository
│   │   │               │   ├── BookRepository.java
│   │   │               │   ├── LoanRepository.java
│   │   │               │   ├── RoleRepository.java
│   │   │               │   └── UserRepository.java
│   │   │               ├── service
│   │   │               │   ├── AuthService.java
│   │   │               │   ├── BookService.java
│   │   │               │   ├── JpaUserDetailsService.java
│   │   │               │   ├── JwtService.java
│   │   │               │   ├── LoanService.java
│   │   │               │   └── UserService.java
│   │   │               ├── Startup.java
│   │   │               └── util
│   │   │                   └── Roles.java
│   │   └── resources
│   │       ├── application-dev.properties
│   │       ├── application.properties
│   │       └── db
│   │           └── migration
│   │               ├── V1_0__create_table.sql
│   │               ├── V2_0__create_table_role.sql
│   │               └── V2_1__alter_table_users.sql
└── README.md
```