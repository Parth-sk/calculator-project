# Calculator DevOps

A Calculator REST API built using Java, Spring Boot, and Maven,
with an end-to-end CI/CD pipeline.

## Technology Stack

- Java 21
- Spring Boot
- Maven
- JUnit 5
- Git and GitHub
- Jenkins
- Docker
- Docker Compose

## Planned API

| Endpoint | Purpose |
|---|---|
| GET /api/v1/add?a=2&b=3 | Addition |
| GET /api/v1/subtract?a=5&b=3 | Subtraction |
| GET /api/v1/multiply?a=2&b=3 | Multiplication |
| GET /api/v1/divide?a=6&b=3 | Division |
| GET /actuator/health | Application health |

The endpoints above are planned and will be implemented incrementally.

## Build and Test

Build the project:

    mvn clean package

Run tests:

    mvn test

## Project Roadmap

- [x] Initialize Spring Boot project
- [ ] Implement REST endpoints and validation
- [ ] Add unit and integration tests
- [ ] Create a multi-stage Dockerfile
- [ ] Configure Docker Compose
- [ ] Automate CI/CD using Jenkins
- [ ] Verify automated deployment
