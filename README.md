# Wallet API

A Spring Boot REST API for managing users, wallets, and transactions. Built for Amazon OA prep — every feature maps to a common bug archetype.

## Tech Stack

- Java 21 + Spring Boot 3.x
- Maven
- Spring Data JPA + H2 (in-memory database)
- Redis (Docker container for caching + auth tokens)
- Spring Security + custom auth filter with JWT-style tokens
- JUnit 5

## What's Working

### Phase 0 — Skeleton
- App boots on port 8080
- `GET /health` returns `{"status": "UP"}`

### Phase 1 — Users + Wallets
- `POST /users` — create a user (password hashed with BCrypt, never exposed in responses)
- `POST /wallets` — create a wallet for a user
- `GET /wallets/{id}` — fetch a wallet by ID

### Phase 2 — Transactions + Balance Rules
- `POST /wallets/{id}/deposit` — deposit amount, updates balance
- `POST /wallets/{id}/withdraw` — withdraw amount, rejects overdraft
- `POST /transfers` — atomic transfer between wallets with `@Transactional`
- `GET /wallets/{id}/transactions` — list all transactions for a wallet
- Negative/zero amounts rejected on all write endpoints

### Phase 3 — Redis Cache-Aside on Balance
- `GET /wallets/{id}` checks Redis first; on miss, reads DB and caches
- Deposit, withdraw, and transfer invalidate the cached balance
- Transfer invalidates both source and destination wallet keys

### Phase 4 — Auth with Token TTL
- `POST /auth/login` — validates credentials, returns a UUID token stored in Redis with 900s TTL
- Custom `AuthFilter` intercepts all requests, checks `Authorization: Bearer <token>` header
- Open paths (no token required): `/health`, `/users`, `/auth/login`
- All other endpoints require a valid, non-expired token
- Expired or invalid tokens return 401 Unauthorized

## Planned

- Phase 5: Rate limiting, blocklist, idempotency

## Prerequisites

- Java 21
- Maven
- Docker Desktop (for Redis)

## Running Locally

1. Start Redis:
```bash
docker run --name redis -p 6379:6379 -d redis
```
If the container already exists: `docker start redis`

2. Run the app:
```bash
./mvnw spring-boot:run
```

App runs at `http://localhost:8080`. H2 in-memory DB resets on every restart — no setup needed.

## Project Structure

```
com.wallet.wallet_app
├── config/
│   ├── SecurityConfig
│   ├── RedisConfig
│   └── AuthFilter
├── controller/
│   ├── HealthController
│   ├── UserController
│   ├── WalletController
│   └── AuthController
├── dto/
│   ├── CreateUserRequest
│   ├── UserResponse
│   ├── LoginRequest
│   ├── TokenResponse
│   ├── CreateWalletRequest
│   ├── WalletResponse
│   ├── AmountRequest
│   ├── TransferRequest
│   └── TransactionResponse
├── entity/
│   ├── User
│   ├── Wallet
│   └── Transaction
├── repository/
│   ├── UserRepository
│   ├── WalletRepository
│   └── TransactionRepository
├── service/
│   ├── UserService
│   ├── WalletService
│   └── AuthService
└── WalletAppApplication
```