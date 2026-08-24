# Wallet API

A Spring Boot REST API for managing users, wallets, and transactions. Built for Amazon OA prep — every feature maps to a common bug archetype.

## Tech Stack

- Java 21 + Spring Boot 3.x
- Maven
- Spring Data JPA + H2 (in-memory database)
- Spring Security
- JUnit 5

## What's Working

### Phase 0 — Skeleton
- App boots on port 8080
- `GET /health` returns `{"status": "UP"}`

### Phase 1 — Users + Wallets
- `POST /users` — create a user (password hashed, never exposed)
- `POST /wallets` — create a wallet for a user
- `GET /wallets/{id}` — fetch a wallet by ID

### Phase 2 — In Progress
- Transaction entity and DTOs defined
- Deposit, withdraw, and transfer endpoints coming next

## Planned

- Phase 3: Redis cache-aside on wallet balance
- Phase 4: Auth with token TTL
- Phase 5: Rate limiting, blocklist, idempotency

## Running Locally

```bash
./mvnw spring-boot:run
```

App runs at `http://localhost:8080`. Uses H2 in-memory DB — no setup needed.