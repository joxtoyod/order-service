# order-service

Order read API for the Acme storefront. Spring Boot 3 / Java 21 / PostgreSQL / Redis.

## Run locally

```bash
cp .env.example .env
docker compose up -d --build
docker compose ps                     # wait until every service is "healthy"
docker compose exec -T db psql -U orders -d orders < db/seed/dev-seed.sql
curl localhost:8080/api/orders/ORD-10001
curl localhost:8080/actuator/info     # shows build version + commit
```

### Feature flags

| Variable | Default | Description |
|---|---|---|
| `FEATURE_ORDER_SUMMARY` | `false` | Enables `GET /api/orders/{orderNumber}/summary` |

Flyway runs migrations automatically on startup (`src/main/resources/db/migration`).

## Tests

```bash
./gradlew test
```

## Debugging

Uncomment `JAVA_TOOL_OPTIONS` in `.env`, then `docker compose up -d api` and
run the **Attach to order-service (Docker)** launch config in VS Code (port 5005).
