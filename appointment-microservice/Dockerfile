# appointment-microservice/Dockerfile
FROM openjdk:21-jdk-slim

# Definir diretório de trabalho
WORKDIR /app

# Copiar arquivos do Maven
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Dar permissão de execução ao mvnw
RUN chmod +x ./mvnw

# Baixar dependências (cache layer)
RUN ./mvnw dependency:go-offline

# Copiar código fonte
COPY src ./src

# Compilar aplicação
RUN ./mvnw clean package -DskipTests

# Encontrar e renomear o JAR para um nome fixo
RUN cp target/*.jar app.jar

# Expor porta
EXPOSE 8080

# Comando para executar a aplicação
CMD ["java", "-jar", "app.jar"]