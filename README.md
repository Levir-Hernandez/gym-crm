# Gym CRM RESTful API
A RESTful API designed as a Gym CRM solution for managing trainers, trainees, workout sessions, and training categories. Built with scalability, maintainability, and security in mind, it follows a clean layered architecture separating controllers, services, repositories, DTOs, and entities.

## Core Features & Spring Modules
- **API Documentation & Exploration**: Fully interactive API documentation for exploring and testing endpoints using OpenAPI/Swagger.
- **Monitoring & Observability**: System health, performance metrics, request latency, and throughput are tracked using Spring Actuator for production-ready monitoring.
- **Authentication & Authorization**: Secure login, role-based access control, password encoding, JWT creation/validation, and route-level protection via Spring Security.
- **Token & Session Management**: Handles brute-force lockouts, token blacklisting, rotation, and secure refresh token handling with Spring Data Redis.
- **RESTful API Design**: Supports hypermedia-driven responses, resource linking, pagination, and Level 3 REST API compliance with Spring HATEOAS.
- **Cross-Cutting Concerns & Logging**: Logging, metrics, and traceability are separated from business logic using Spring AOP, keeping the core codebase clean and maintainable.
- **Data Persistence & Custom Queries**: Simplified CRUD operations, advanced JPQL and CriteriaQuery support, and pagination via Spring Data JPA.

## Testing
- **JUnit5 + RestAssured**: Comprehensive testing of controllers, services, and repositories to ensure reliability and correctness.

## Note
This project was enhanced with Mockito unit tests, BDD, and integration tests using Testcontainers, and is part of a larger microservice project. You can explore it [here](https://github.com/Levir-Hernandez/gym-microservices), where all Dockerfiles and Docker Compose setups are included to run services independently or together.