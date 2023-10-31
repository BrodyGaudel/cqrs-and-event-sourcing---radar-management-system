# README.md

## Distributed System for Vehicle Speed Violation Management

This application is a distributed system based on microservices architecture, employing an event-driven approach following the Event Sourcing and Command Query Responsibility Segregation (CQRS) patterns. Its primary purpose is to manage traffic violations resulting from speeding incidents detected by automatic radar systems.
---
![Architecture Overview](https://github.com/BrodyGaudel/cqrs-and-event-sourcing---radar-management-system/blob/main/architecture.jpg)
---
![Architecture Overview](https://github.com/BrodyGaudel/cqrs-and-event-sourcing---radar-management-system/blob/main/illustrations/13.png)

### Microservices Overview

1. **radar-service**
   - Responsible for radar management (command & query operations).

2. **registration-service**
   - Manages vehicle information, including registration (command & query operations).

3. **infraction-service**
   - Handles the management of traffic violations (command & query operations).

### Additional Services

- **discovery-service** (Spring Cloud Eureka Server)
  - Enables service discovery within the application.

- **gateway-service** (Spring Cloud Gateway)
  - Acts as a gateway for routing and load balancing.

### Technology Stack

- **Axon Framework and Axon Server**
  - Utilized for implementing Event Sourcing and CQRS.

- **MySQL**
  - Database management system for persistent data storage.

- **Maven**
  - Build and project management tool.

- **Java 17**
  - Programming language for application development.

### Getting Started

1. Clone the repository.
   ```bash
   git clone https://github.com/BrodyGaudel/cqrs-and-event-sourcing---radar-management-system.git
   ```

2. Navigate to the project directory.
   ```bash
   cd your/project/directory
   ```

3. Build the project using Maven.
   ```bash
   mvn clean install
   ```

4. Run the services using your preferred method (e.g., Docker, Java command).

### Configuration

- Update configuration files in each microservice for database connection, Axon Server, and other relevant settings.

### Contribution Guidelines

- Fork the repository, make changes, and submit a pull request.

---

### Illustration

### Author
- Brody Gaudel