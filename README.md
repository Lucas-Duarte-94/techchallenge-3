# Tech Challenge Fase 3 - Microserviços com RabbitMQ

Este projeto implementa uma arquitetura de microserviços usando Spring Boot e RabbitMQ para comunicação assíncrona entre serviços.

## 🏗️ Arquitetura

O projeto é composto por 3 componentes principais:

- **Appointment Microservice**: Serviço principal de agendamentos com GraphQL e H2 Database
- **RabbitMQ Consumer**: Serviço consumidor de mensagens para processamento assíncrono
- **RabbitMQ**: Message broker para comunicação entre microserviços

## 🛠️ Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.4.5**
- **Spring AMQP** (RabbitMQ)
- **GraphQL** (Spring GraphQL)
- **H2 Database** (In-memory)
- **Docker & Docker Compose**
- **Maven** (Build tool)

## 📁 Estrutura do Projeto

```
techChallenge fase 3/
├── docker-compose.yml
├── README.md
├── appointment-microservice/
│   ├── Dockerfile
│   ├── pom.xml
│   ├── mvnw
│   ├── .mvn/
│   └── src/
│       └── main/
│           └── resources/
│               ├── application.yml
│               └── application-docker.yml
└── RabbitMQ-consumer/
    ├── Dockerfile
    ├── pom.xml
    ├── mvnw
    ├── .mvn/
    └── src/
        └── main/
            └── resources/
                ├── application.yml
                └── application-docker.yml
```

## 🚀 Como Executar

### Pré-requisitos

- Docker
- Docker Compose

### Executando com Docker Compose

1. **Clone o repositório** (se aplicável)

2. **Execute todos os serviços:**
   ```bash
   docker-compose up --build
   ```

3. **Para executar em background:**
   ```bash
   docker-compose up --build -d
   ```

4. **Para ver os logs:**
   ```bash
   docker-compose logs -f
   ```

5. **Para parar os serviços:**
   ```bash
   docker-compose down
   ```

### Comandos Úteis

```bash
# Rebuild apenas um serviço específico
docker-compose build appointment-service
docker-compose up appointment-service

# Limpar containers e rebuild completo
docker-compose down
docker system prune -f
docker-compose up --build

# Ver logs de um serviço específico
docker-compose logs -f appointment-service
```

## 🌐 URLs de Acesso

Após executar o `docker-compose up`, os serviços estarão disponíveis em:

| Serviço | URL | Descrição |
|---------|-----|-----------|
| **Appointment Service** | http://localhost:8080 | API principal do serviço de agendamentos |
| **GraphiQL** | http://localhost:8080/graphiql | Interface GraphQL para testes |
| **H2 Console** | http://localhost:8080/h2-console | Console do banco H2 |
| **Consumer Service** | http://localhost:8081 | Serviço consumidor RabbitMQ |
| **RabbitMQ Management** | http://localhost:15672 | Interface de gerenciamento RabbitMQ |

