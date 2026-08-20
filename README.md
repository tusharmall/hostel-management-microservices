# Hostel Management Microservices

A Hostel Management backend built with **Java, Spring Boot, Spring Cloud, REST APIs, MySQL, Docker, and AWS EC2**.

The original application was converted from a monolithic architecture into independent **Course Service** and **Room Service** microservices. The application uses **Eureka Service Discovery, Spring Cloud API Gateway, OpenFeign, Spring Security with JWT, Swagger/OpenAPI, Docker Compose, and separate MySQL databases** for the services.

---

## Architecture

```text
                         Client / Postman / Swagger
                                  |
                                  v
                         +------------------+
                         |   API Gateway    |
                         |      :8080       |
                         +--------+---------+
                                  |
                       +----------+----------+
                       |                     |
                       v                     v
              +----------------+    +----------------+
              | Course Service |    |  Room Service  |
              |     :8081      |    |     :8082      |
              +-------+--------+    +-------+--------+
                      |                     |
                      v                     v
                +-----------+         +-----------+
                | course_db |         |  room_db  |
                +-----------+         +-----------+

                         +------------------+
                         | Eureka Registry  |
                         |      :8762       |
                         +------------------+

Course Service
      |
      | OpenFeign
      v
Room Service
```

The complete application is containerized with Docker and Docker Compose and can be deployed on AWS EC2.

---

## Key Features

* Microservices architecture with independent Course and Room services
* Eureka-based service discovery
* Spring Cloud API Gateway for centralized routing
* OpenFeign for service-to-service communication
* RESTful CRUD APIs
* DTO-based request and response models
* ModelMapper for entity/DTO conversion
* Bean Validation
* Global exception handling
* Custom business exceptions
* Pagination and sorting
* Search APIs
* Swagger/OpenAPI documentation
* Unified Swagger UI through the API Gateway
* Spring Security and JWT authentication
* Role-based endpoint protection
* SLF4J logging
* MySQL database per service
* Docker containerization
* Docker Compose orchestration
* AWS EC2 deployment

---

## Security

The application uses **Spring Security and JWT authentication**.

Authentication flow:

```text
POST /auth/login
       |
       v
AuthenticationManager
       |
       v
UserDetailsService
       |
       v
User Repository
       |
       v
JWT Generated
       |
       v
Client
```

For protected APIs:

```text
Authorization: Bearer <JWT>
```

The JWT filter validates the token before allowing access to protected endpoints.

---

## Validation and Exception Handling

Incoming API data is validated using Bean Validation.

Examples:

```text
@NotBlank
@Size
@Min
@Max
@Valid
```

Invalid requests are handled by a centralized:

```text
@RestControllerAdvice
```

Custom business exceptions such as:

```text
CourseNotFoundException
```

are converted into structured HTTP responses.

Example:

```json
{
  "message": "Course not found with id 500",
  "status": 404,
  "timestamp": "..."
}
```

---

## Pagination, Sorting and Search

Pagination:

```text
GET /courses?page=0&size=10
```

Sorting:

```text
GET /courses?page=0&size=10&sort=name,asc
```

Search:

```text
GET /courses/search?name=Java
```

Search with multiple conditions:

```text
GET /courses/search-advanced?name=Java&duration=6
```

---

## Swagger / OpenAPI( Screeshots)
<img width="1917" height="1020" alt="Screenshot 2026-08-17 165701" src="https://github.com/user-attachments/assets/ac910e3e-6bee-4b4c-9e0c-90e6f2335434" />
<img width="1907" height="1025" alt="Screenshot 2026-08-17 165649" src="https://github.com/user-attachments/assets/f10a602f-7e39-45d4-bf07-abd7ddcda95e" />
<img width="1292" height="955" alt="image" src="https://github.com/user-attachments/assets/1f39f3fb-9931-4f72-abf3-5e68a9ea4ab0" />
<img width="1292" height="621" alt="image" src="https://github.com/user-attachments/assets/f4ae5bc1-30b5-4c30-a2b9-49ed8243ac67" />


<img width="1257" height="175" alt="Screenshot 2026-08-20 164703" src="https://github.com/user-attachments/assets/b2f8f71d-28dc-474f-8cb4-dc88e7e220df" />
<img width="1258" height="312" alt="Screenshot 2026-08-20 164632" src="https://github.com/user-attachments/assets/a2e72542-026e-44cb-91f2-2f6da76e3bdd" />



A unified Swagger UI is exposed through the API Gateway.

The Gateway aggregates the OpenAPI specifications of:

```text
Course Service
Room Service
```

Backend API documentation is exposed through:

```text
/courses/v3/api-docs
/rooms/v3/api-docs
```

The Gateway presents both services through a single Swagger UI.

Local example:

```text
http://localhost:8080/swagger-ui.html
```

For a deployed environment:

```text
http://<SERVER-IP>:8080/swagger-ui.html
```

---

## Technology Stack

### Backend

* Java 17
* Spring Boot
* Spring MVC
* Spring Data JPA
* Hibernate
* Spring Security
* JWT
* Spring Cloud
* Eureka
* Spring Cloud Gateway
* OpenFeign

### Database

* MySQL 8

### Tools

* Maven
* Git
* GitHub
* Postman
* Swagger/OpenAPI
* Docker
* Docker Compose

### Cloud

* AWS EC2

---

## Microservices

### 1. Course Service

Responsible for Course-related operations.

Example APIs:

```text
GET    /courses
GET    /courses/{id}
POST   /courses
PUT    /courses/{id}
DELETE /courses/{id}

GET    /courses/search?name=Java
GET    /courses/search-duration?duration=6
GET    /courses/search-advanced?name=Java&duration=6
```

The service uses its own database:

```text
course_db
```

---

### 2. Room Service

Responsible for Room-related operations.

Example APIs:

```text
GET    /rooms
POST   /rooms
PUT    /rooms/{id}
DELETE /rooms/{id}
```

The service uses its own database:

```text
room_db
```

---

### 3. Service Registry

Uses **Netflix Eureka** for service discovery.

Services register themselves with Eureka:

```text
COURSE-SERVICE
ROOM-SERVICE
API-GATEWAY
```

Instead of hardcoding service IP addresses and ports, services can discover each other through Eureka.

---

### 4. API Gateway

The Gateway provides a single entry point for clients.

Example:

```text
Client
   |
   v
API Gateway :8080
   |
   +---- /courses/** ---> COURSE-SERVICE
   |
   +---- /rooms/** -----> ROOM-SERVICE
   |
   +---- /auth/** ------> COURSE-SERVICE
```

The Gateway uses load-balanced service discovery:

```text
lb://COURSE-SERVICE
lb://ROOM-SERVICE
```

---

## Inter-Service Communication

Course Service uses **OpenFeign** to communicate with Room Service.

The flow is:

```text
Course Service
       |
       v
   Feign Client
       |
       v
     Eureka
       |
       v
 Room Service
```

This avoids hardcoding:

```text
http://localhost:8082
```

and instead uses the service name registered with Eureka.

---

## Request Flow

A request coming through the Gateway follows this general path:

```text
Postman / Browser
        |
        v
API Gateway
        |
        v
Eureka Service Discovery
        |
        v
Course / Room Service
        |
        v
Controller
        |
        v
Service Layer
        |
        v
Repository
        |
        v
Hibernate / JPA
        |
        v
MySQL
        |
        v
JSON Response
        |
        v
API Gateway
        |
        v
Client
```

For service-to-service communication:

```text
Course Service
      |
      v
OpenFeign
      |
      v
Eureka
      |
      v
Room Service
```

---

## Layered Architecture

Each service follows a layered structure:

```text
Controller
    |
    v
Service
    |
    v
Repository
    |
    v
Entity
    |
    v
Database
```

DTOs are used at the API boundary:

```text
Request JSON
     |
     v
Request DTO
     |
     v
Service
     |
     v
Entity
```

and:

```text
Entity
   |
   v
Response DTO
   |
   v
JSON Response
```

ModelMapper is used for DTO/entity conversion.

---


## Docker Architecture

The complete application is containerized.

```text
Docker Compose
     |
     +-- Eureka Server
     |
     +-- API Gateway
     |
     +-- Course Service
     |
     +-- Room Service
     |
     +-- MySQL Course DB
     |
     +-- MySQL Room DB
```

Each Spring Boot service has its own Docker image.

The two MySQL services use persistent Docker volumes.

---

## Docker Compose

Start the complete stack:

```bash
docker compose up -d
```

Check running services:

```bash
docker compose ps
```

View logs:

```bash
docker compose logs -f
```

Stop the application:

```bash
docker compose down
```

Rebuild and start:

```bash
docker compose up -d --build
```

---

## AWS EC2 Deployment

The complete Dockerized application was deployed on an **AWS EC2 Ubuntu server**.

Deployment flow:

```text
GitHub Repository
       |
       v
AWS EC2
       |
       v
Docker
       |
       v
Docker Compose
       |
       v
Complete Microservices Stack
```

The Gateway acts as the external entry point.

```text
Internet
    |
    v
AWS EC2
    |
    v
API Gateway :8080
    |
    +---- Course Service
    |
    +---- Room Service
```

> Note: The EC2 instance may be stopped when the application is not being demonstrated to control cloud costs.

---

## API Testing

APIs were tested using Postman and Swagger.

Example Gateway requests:

```text
GET    http://localhost:8080/courses
GET    http://localhost:8080/courses/1
POST   http://localhost:8080/courses
PUT    http://localhost:8080/courses/1
DELETE http://localhost:8080/courses/1

GET    http://localhost:8080/rooms
```

JWT-protected APIs require:

```text
Authorization: Bearer <JWT>
```

---

## Example Course Request

```json
{
  "name": "Java Full Stack",
  "durationInMonths": 6
}
```

Example response:

```json
{
  "id": 1,
  "name": "Java Full Stack",
  "durationInMonths": 6
}
```

---

## Project Structure

```text
hostel-management-microservices/
│
├── api-gateway/
│   ├── src/
│   ├── Dockerfile
│   └── pom.xml
│
├── service-registry/
│   ├── src/
│   ├── Dockerfile
│   └── pom.xml
│
├── course-service/
│   ├── src/
│   ├── Dockerfile
│   └── pom.xml
│
├── room-service/
│   ├── src/
│   ├── Dockerfile
│   └── pom.xml
│
└── docker-compose.yml
```

---

## How to Run Locally

### Prerequisites

```text
Java 17
Git
Docker
Docker Compose
```

Clone the repository:

```bash
git clone <YOUR_GITHUB_REPOSITORY>
```

Move into the project:

```bash
cd hostel-management-microservices
```

Start all services:

```bash
docker compose up -d --build
```

Check:

```bash
docker compose ps
```

Open Swagger:

```text
http://localhost:8080/swagger-ui.html
```

---

## Troubleshooting

View service logs:

```bash
docker compose logs course-service
docker compose logs room-service
docker compose logs api-gateway
docker compose logs eureka-server
```

Check all containers:

```bash
docker compose ps
```

Check Docker images:

```bash
docker images
```

---

## Key Design Decisions

### Why Microservices?

The original application was decomposed so Course and Room functionality could be developed, deployed, and scaled independently.

### Why Eureka?

To avoid hardcoded service URLs and provide dynamic service discovery.

### Why API Gateway?

To provide a centralized entry point for clients and control routing.

### Why OpenFeign?

To simplify synchronous service-to-service communication.

### Why DTOs?

To avoid exposing JPA entities directly through the API and keep API contracts separate from the database model.

### Why Docker?

To provide consistent and reproducible application environments.

### Why separate MySQL databases?

To maintain service-level data ownership and reduce coupling between services.

---

## Challenges Solved

During development, several practical issues were identified and resolved, including:

* Spring Boot and Spring Cloud version compatibility
* Maven and Java version mismatches
* Eureka registration issues
* Docker container networking
* MySQL connectivity from containers
* Gateway routing problems
* Swagger service URL and hostname issues
* JWT/Spring Security configuration
* Docker build resource constraints on EC2
* Git merge and configuration conflicts

These debugging experiences helped validate the system beyond simple local CRUD development.

---

## Future Enhancements

Planned improvements include:

```text
JUnit + Mockito
CI/CD with GitHub Actions
Kafka event-driven communication
Redis caching
Actuator / monitoring
HTTPS + custom domain
Improved secrets management
```

---

## Author

**Tushar Mall**

Java Backend Developer | Spring Boot | Microservices | AWS


```
```
