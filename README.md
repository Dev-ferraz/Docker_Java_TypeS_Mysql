# ⚙️ Projeto Cadastro Conexão

Sistema de cadastro e autenticação com **Spring Boot (Java) no backend** e **TypeScript no frontend**, integrado com **MySQL** e Docker.  

Toda execução é possível dentro do **VS Code**, utilizando o terminal integrado.

---
## 🧱 Backend - Sistema de Cadastro e Autenticação (Spring Boot 3.3.4 + Java 21)

Este projeto é o **backend** da aplicação **cadastro_conexao**, desenvolvido com **Java 21 e Spring Boot 3.3.4**.  
A aplicação fornece endpoints REST para cadastro, login com **JWT**, envio de e-mails para validação e redefinição de senha, e integração com banco de dados **MySQL**.

### 🧠 Tecnologias Principais

- ☕ **Java 21 (Microsoft JDK 21.0.4.7-hotspot)**
- 🌱 **Spring Boot 3.3.4**
- 🛠 **Maven 3.9.8**
- 🐬 **MySQL 8.0**

- 🔐 **JWT (io.jsonwebtoken)**
- ✉️ **Spring Mail + Thymeleaf**
- 💬 **Lombok** — simplificação de código (Getters/Setters automáticos)  
- 🐳 **Docker / Docker Compose**
- **ORM:** Spring Data JPA / Hibernate  
- **Segurança:** Spring Security + JWT  
- **Validação:** Bean Validation (Jakarta Validation API)  
- **Envio de e-mails:** Spring Boot Starter Mail  
- **Templates:** Thymeleaf  
- **Banco de Teste:** H2 Database  
- **Ferramenta de Build:** Maven Compiler Plugin
  

🔗 Download sites:
 
👉 Site Java (JDK 21.0.4.7-hotspot)
https://learn.microsoft.com/en-us/java/openjdk/download?utm_source=chatgpt.com


👉 Site Spring Boot 3.3.4
https://spring.io/projects/spring-framework?utm_source=chatgpt.com


👉 Site Maven 3.9.8
https://maven.apache.org/download.cgi?utm_source=chatgpt.com


👉 Site DBeaver
https://dbeaver.io/download/?utm_source=chatgpt.com


👉 Site MySQL 8.0
https://dev.mysql.com/downloads/installer/?utm_source=chatgpt.com

👉 direto docker:
 https://desktop.docker.com/win/stable/Docker%20Desktop%20Installer.exe


### 🐬 Banco de Dados MySQL (via DBeaver)

O projeto utiliza **MySQL 8.0** como banco de dados principal, que pode ser gerenciado via **DBeaver**:
- **Host:** `localhost`  
- **Porta:** `3308`  
- **Database:** `gerenciamento_usuarios_TF`  
- **Usuário:** `root`  
- **Senha:** `root`  

**Passos para conectar no DBeaver:**
1. Abrir o DBeaver e clicar em **Database → New Database Connection**.  
2. Selecionar **MySQL**.  
3. Preencher os campos:
- **Host:** `localhost`  
- **Porta:** `3308`  
- **Database:** `gerenciamento_usuarios_TF`  
- **Username:** `root`  
- **Password:** `root`  
4. Testar conexão e salvar.  

 Após isso, você poderá navegar pelas tabelas, executar queries SQL e gerenciar os dados do projeto diretamente pelo DBeaver.



### 🐳 Infraestrutura
- **Docker** — criação de containers isolados  
- **Docker Compose** — orquestração dos serviços  
- **Git / GitHub** — versionamento e hospedagem de código  
- **GitHub Actions (opcional)** — pipeline CI/CD automatizado

---

### 💻 Comando para rodar o backend (Docker) na raiz do projeto
```bash
# 1. Gerar o novo JAR com as alterações, ignorando os testes
mvn clean package -DskipTests

# 2. Parar e remover containers antigos
docker-compose down

# 3. Rebuild da imagem com o novo JAR e subir tudo novamente
docker-compose up --build
