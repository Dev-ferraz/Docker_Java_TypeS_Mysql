# ==============================
# Backend Java 21
# ==============================

# Usar imagem base do OpenJDK 21
FROM eclipse-temurin:21-jdk

# Definir diretório de trabalho dentro do container
WORKDIR /app

# Copiar o JAR da aplicação para dentro do container
COPY target/cadastro_conexao-0.0.1-SNAPSHOT.jar app.jar

# Expor a porta que a aplicação irá rodar
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
