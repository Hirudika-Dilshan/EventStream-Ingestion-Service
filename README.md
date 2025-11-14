# 🚀 EventStream: High-Throughput Ingestion Service

This project is a mock-interview assignment to build a mission-critical, high-throughput (1000+ TPS) event ingestion service using Spring Boot.

The service exposes a single `POST /api/v1/event` endpoint that accepts event data, immediately returns a `202 Accepted` response, and processes/saves the event to a database asynchronously.

## ✅ Core Requirements Met

* **High-Availability:** Asynchronous processing (`@Async`) ensures the endpoint is non-blocking.
* **DevOps Support:** Includes Spring Boot Actuator (`/actuator/health`), a multi-stage `Dockerfile`, and a `docker-compose.yml` for running the app and a MySQL database.
* **Reliability:** Global exception handling (`@RestControllerAdvice`) and validation (`@Valid`) are implemented.
* **Configuration:** Database configuration is externalized via `application.properties` and overridden by environment variables in `docker-compose`.

## 🛠️ Tech Stack

* **Java 17**
* **Spring Boot 3+**
* **Spring Web:** For REST API
* **Spring Data JPA:** For database interaction
* **Spring Boot Actuator:** For health checks
* **Maven:** For build management
* **MySQL:** (via Docker) Production database
* **H2:** (Embedded) For local testing
* **Docker & Docker Compose:** For containerization

---

## 🏃 How to Run

There are two ways to run the application:

### 1. Local Development (with H2 In-Memory Database)

You can run the application directly from your IDE (e.g., IntelliJ, VS Code).

1.  Open the project in your IDE.
2.  Navigate to `src/main/java/com/eventstream/ingestionservice/IngestionServiceApplication.java`.
3.  Run the `main` method.
4.  The application will start on `http://localhost:8080`.
5.  The H2 in-memory database console will be available at `http://localhost:8080/h2-console` (use `jdbc:h2:mem:eventdb`, `sa`, `password`).

### 2. Production-like (with Docker Compose & MySQL)

This is the recommended way to test the full system as described in the assignment.

**Prerequisites:**
* Docker Desktop must be installed and running.

**Steps:**

1.  **Build the Project:**
    Open a terminal in the project's root directory and run the Maven build command to create the `.jar` file.
    ```bash
    mvn clean install -DskipTests
    ```

2.  **Run with Docker Compose:**
    In the same terminal, run the following command. This will build the Docker image for the app and start both the `app-service` and `mysql-db` containers.
    ```bash
    docker-compose up --build
    ```

3.  The application will be available at `http://localhost:8080`.
4.  The MySQL database will be running on `localhost:3307`.

---

## 🔬 How to Test

### Health Check

* **URL:** `http://localhost:8080/actuator/health`
* **Method:** `GET`
* **Success Response:** `{"status":"UP", ...}`

### Event Ingestion (Happy Path)

* **URL:** `http://localhost:8080/api/v1/event`
* **Method:** `POST`
* **Body (JSON):**
    ```json
    {
      "eventType": "user_login",
      "userId": "uuid-a1b2-c3d4-e5f6",
      "timestamp": "2025-11-09T18:30:00Z",
      "eventData": {
        "ipAddress": "192.168.1.1",
        "device": "mobile"
      }
    }
    ```
* **Success Response:**
    * **Status Code:** `202 Accepted`
    * **Body:** (Empty)

### Event Ingestion (Validation Error)

* **URL:** `http://localhost:8080/api/v1/event`
* **Method:** `POST`
* **Body (JSON):** (Missing `userId`)
    ```json
    {
      "eventType": "user_login",
      "timestamp": "2025-11-09T18:30:00Z",
      "eventData": {
        "ipAddress": "192.168.1.1",
        "device": "mobile"
      }
    }
    ```
* **Error Response:**
    * **Status Code:** `400 Bad Request`
    * **Body:**
        ```json
        {
          "userId": "userId cannot be null or empty"
        }
        ```