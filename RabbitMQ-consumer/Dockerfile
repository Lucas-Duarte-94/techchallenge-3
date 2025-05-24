# RabbitMQ-consumer/Dockerfile
FROM openjdk:21-jdk-slim

# Instalar Maven
RUN apt-get update && \
    apt-get install -y maven && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

# Definir diretório de trabalho
WORKDIR /app

# Copiar apenas pom.xml primeiro (para aproveitar cache do Docker)
COPY pom.xml ./

# Baixar dependências (cache layer)
RUN mvn dependency:go-offline -B

# Copiar código fonte
COPY src ./src

# Compilar aplicação
RUN mvn clean package -DskipTests

# Encontrar e renomear o JAR para um nome fixo
RUN cp target/*.jar app.jar

# Expor porta
EXPOSE 8081

# Comando para executar a aplicação
CMD ["java", "-jar", "app.jar"]