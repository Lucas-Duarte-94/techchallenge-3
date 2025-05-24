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

### 🖥️ Interface Gráfica
- **GraphiQL:** `http://localhost:8080/graphiql` (interface web para testes)

### 📋 Schema Disponível

#### **Queries (Consultas)**

##### Consultas de Usuários:
```graphql
# Buscar usuário por email
query {
  getUserByEmail(email: "user@example.com") {
    id
    name
    email
    type
  }
}

# Buscar usuário por ID
query {
  getUserById(id: "1") {
    id
    name
    email
    type
  }
}

# Listar todos os usuários (requer email do solicitante)
query {
  getAllUsers(email: "admin@example.com") {
    id
    name
    email
    type
  }
}
```

##### Consultas de Agendamentos:
```graphql
# Buscar agendamento por ID
query {
  getAppointmentById(id: "1", email: "user@example.com") {
    id
    startDateTime
    endDateTime
  }
}

# Buscar próprios agendamentos
query {
  getSelfAppointments(email: "patient@example.com") {
    id
    startDateTime
    endDateTime
  }
}

# Buscar agendamentos de um paciente específico (para médicos)
query {
  getAllPatientAppointmentsByEmail(
    doctorEmail: "doctor@example.com",
    patientEmail: "patient@example.com"
  ) {
    id
    startDateTime
    endDateTime
  }
}
```

#### **Mutations (Modificações)**

##### Operações de Usuários:
```graphql
# Criar novo usuário
mutation {
  createUser(requestDTO: {
    name: "João Silva"
    email: "joao@example.com"
    password: "senha123"
    type: PATIENT
  }) {
    id
    name
    email
    type
  }
}

# Deletar usuário
mutation {
  deleteUserById(id: "1")
}
```

##### Operações de Agendamentos:
```graphql
# Criar agendamento
mutation {
  createAppointment(
    appointment: {
      userId: "1"
      doctorId: "2"
      startDateTime: "2024-01-15T09:00:00"
      endDateTime: "2024-01-15T10:00:00"
    },
    nurserEmail: "nurse@example.com"
  ) {
    id
    startDateTime
    endDateTime
  }
}

# Atualizar agendamento
mutation {
  updateAppointment(
    doctorEmail: "doctor@example.com",
    appointment: {
      id: "1"
      doctorId: "2"
      userId: "3"
      startDateTime: "2024-01-15T14:00:00"
      endDateTime: "2024-01-15T15:00:00"
    }
  ) {
    id
    startDateTime
    endDateTime
  }
}

# Deletar agendamento
mutation {
  deleteAppointment(id: "1", doctorEmail: "doctor@example.com")
}
```

### 🧪 Exemplos de Requisições HTTP

#### Usando Postman/Insomnia:
```json
{
  "query": "query { getUserByEmail(email: \"user@example.com\") { id name email type } }"
}
```

```json
{
  "query": "mutation { createAppointment(appointment: { userId: \"1\", doctorId: \"2\", startDateTime: \"2024-01-15T09:00:00\", endDateTime: \"2024-01-15T10:00:00\" }, nurserEmail: \"nurse@example.com\") { id startDateTime endDateTime } }"
}
```

### 📊 Tipos de Dados

#### **UserEnum:**
- `ADMIN` - Administrador do sistema
- `DOCTOR` - Médico
- `NURSER` - Enfermeiro
- `PATIENT` - Paciente

#### **DateTime:**
- Formato: ISO 8601 (`YYYY-MM-DDTHH:mm:ss`)
- Exemplo: `"2024-01-15T09:00:00"`
