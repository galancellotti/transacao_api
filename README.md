## 💻 Sobre o projeto

Esta é uma API de transações bancárias simples, desenvolvida com foco em funcionalidades como transferência entre
usuários, consulta de extrato e cadastro. Também inclui autenticação e autorização via token JWT.

O objetivo do projeto é demonstrar boas práticas na construção de APIs REST usando Spring Boot, com foco em segurança,
organização de código e regras de negócio claras.

## 🚀 Tecnologias utilizadas

- **[Java 21](https://www.oracle.com/java)**
- **[Spring Boot 3](https://spring.io/projects/spring-boot)**
- **[Maven](https://maven.apache.org)**
- **[PostgresSQL](https://www.postgresql.org/)**
- **[Spring Data JPA](https://spring.io/projects/spring-data-jpa)**
- **[Hibernate](https://hibernate.org)**
- **[Flyway](https://flywaydb.org)**
- **[JWT](https://www.jwt.io/)**

## 📁 Estrutura do Projeto

```
src
└── main
    └── java
        └── com.basicTransaction_api
            ├── controllers              # Camada responsável pelos endpoints
            ├── domain
            │   ├── dto                  # Data Transfer Objects
            │   ├── entity               # Entidades JPA
            │   └── exceptions           # Classes de exceções personalizadas
            ├── repository               # Spring Data JPA
            ├── security                 # Configurações de segurança (JWT, config, Filter)
            ├── service                  # Camada de regras de negócio (Services)
            ├── springDoc                # Configuração do Swagger/OpenAPI
            └── TransactionApiApplication.java # Classe principal da aplicação Spring Boot

```

## 📁 Recursos e Configurações

```
resources
├── db.migration                         # Scripts de criação de tabelas para Flyway
│   ├── V1__create-table-users.sql       # Criação da tabela de usuários
│   └── V2__create-table-transaction.sql # Criação da tabela de transações
├── static                              
├── templates                            
├── application.properties               # Configurações principais da aplicação
└── application.test.properties          # Configurações específicas para testes
```

🧪 Estrutura de Testes

```
test
└── java
    └── com.basicTransaction_api
        ├── controllers                  # Testes dos controllers 
        ├── repository                   # Testes dos repositórios
        ├── service                      # Testes da lógica de negócio
        └── TransactionApiApplicationTests.java # Classe de teste da aplicação principal
```

## ⚙️ Funcionalidades

- [x] Cadastro de usuários;
- [x] Autenticação de usuários e retorno do Token Jwt;
- [x] Autorização de usuários via token JWT em requisições;
- [x] Criptografia de senhas com BCrypt;
- [x] Envio de transações entre usuários;
- [x] Retorno do extrato de transações do usuário;
- [x] Testes Automatizados

## 🔗 Endpoints

| Método | Endpoint          | Descrição                          |
|--------|-------------------|------------------------------------|
| POST   | /login            | Login de usuários e retorno Jwt    |
| POST   | /register         | Cadastro de usuários               |
| POST   | /transaction      | Envio de transações                |
| GET    | /transaction/{id} | Retorna todas as transações por Id |

## 📄 Documentação Swagger

Após iniciar o servidor, acesse:

```
   http://localhost:8080/swagger-ui/index.html
```

Lá você poderá testar todos os endpoints da API com autenticação JWT e ver descrições das rotas.

## 👨‍💻 Desenvolvido por

**Gabriel Lancellotti**  
Estudante de Java  
[🔗 LinkedIn](https://www.linkedin.com/in/gabriel-lancellotti-349a1b311/)**


