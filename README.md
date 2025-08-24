# 🏗️ Microservices Architecture with Spring Boot, Docker & API Gateway

This project is a **multi-module Maven** application that demonstrates a simple **microservices-based architecture** using **Spring Boot**, **Spring Cloud**, **Docker**, and **Databases (MySQL, MongoDB, H2)**.  

---

## 📌 Features
- **User Service** → manages users (uses H2 database)
- **Product Service** → manages products (uses MongoDB)
- **Order Service** → manages orders (uses MySQL, communicates with User & Product services)
- **API Gateway** → entry point for all clients (routes requests to services)
- **Service Communication** via **REST** & **Feign Clients**
- **Docker Compose** to run all services + databases
- **Health Checks** for container orchestration
- **Swagger (OpenAPI)** for API documentation

---

## 🏗️ Project Structure
