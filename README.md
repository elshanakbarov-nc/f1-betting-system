# 🏎️ Bet Service

A **Spring Boot**–based microservice for managing **bets** on F1 events, handling **event outcomes**, and processing **user rewards**.  
Demonstrates event-driven architecture with **Kafka**, persistence with **JPA/Hibernate**, and a clean separation of domain logic.

---

## 🧩 Overview

The **Bet Service** allows users to:

1. **Place bets** via a REST API
    - Bets are persisted and can be retrieved by user or event.
2. **Publish and consume event outcomes** via **Kafka**
    - When an event outcome is processed, a Kafka message is sent and consumed to resolve all related bets.
3. **Evaluate and update user balances** based on bet results.

---

## 🏗️ Architecture

```
        +-------------+
        |  REST API   |  <-- POST /api/bets
        +-------------+
                |
                v
        +-------------------+
        | BetService        |
        | (Persist bet)     |
        +-------------------+
                |
                v
        +-------------------+
        | Kafka Producer    |
        | (Event outcome)   |
        +-------------------+
                |
                v
        +-------------------+
        | Kafka Consumer    |
        | (Resolve bets)    |
        +-------------------+
                |
                v
        +-------------------+
        | User/Bet Update   |
        +-------------------+
```

---

## ⚙️ Technologies

| Component  | Tech |
|-------------|------|
| Language | Java 17+ |
| Framework | Spring Boot 3 |
| Messaging | Apache Kafka |
| Database | H2 (in-memory) |
| ORM | Spring Data JPA / Hibernate |
| Build Tool | Gradle |
| Logging | SLF4J + Logback |

---

## 🚀 Running the Application

### 1️⃣ Prerequisites
- Java 17 or higher
- Gradle
- Kafka & Zookeeper running locally (or via Docker)

### 2️⃣ Using Docker Compose

```bash
docker compose build --no-cache app
docker compose up -d
```

- **Kafka**: available at `localhost:9092`
- **Spring Boot**: runs at [http://localhost:8080](http://localhost:8080)
- **Database**: in-memory H2 initialized at startup

---

## 🧠 API Endpoints

### 🎯 Place a Bet

**POST** `/api/bets`  
**Content-Type:** `application/json`

```json
{
  "userId": 1,
  "eventId": "3d63d0f6-6dc5-405b-94c2-6c7d2f404455",
  "driverId": "1",
  "amount": 100.00
}
```

### 🏁 Submit Event Outcome

**POST** `/api/events/outcome`  
**Content-Type:** `application/json`

```json
{
  "eventId": "3d63d0f6-6dc5-405b-94c2-6c7d2f404455",
  "winningDriverId": "1"
}
```

➡️ This publishes a Kafka message and triggers bet resolution for the event.

---

## 🧾 H2 Database Console

To explore data during development:
- Open: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- Use the following credentials:

| Property | Value |
|-----------|--------|
| JDBC URL | `jdbc:h2:mem:testdb` |
| Username | `sa` |
| Password | *(empty)* |

---

## ⚙️ Configuration Reference

### H2 Database

```properties
spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true
```

### Kafka

```properties
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer
spring.kafka.consumer.value-deserializer=org.springframework.kafka.support.serializer.JsonDeserializer
```

### Topics

```properties
app.topics.events=f1-events
app.topics.events.dlq=f1-events.dlq
```

---

## 🏁 Summary

✅ Implements core features for F1 betting:  
**Bet creation → Kafka event → event outcome processing → bet resolution → user balance update**

Designed to be **modular**, **testable**, and **extensible** with clear layering and separation of concerns.

---

**Author:** Elshan Akbarov  
**Year:** 2025  
**Tech Stack:** Spring Boot, Kafka, H2, Gradle, Java 17+
