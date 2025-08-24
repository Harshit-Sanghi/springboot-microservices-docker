# Microservices (User, Order, Product) — Business Logic + Docker

**Modules**
- `user-service` — H2 (in-memory), CRUD users (port 8081)
- `order-service` — MySQL, CRUD orders (port 8082), uses **OpenFeign** to call user-service & product-service
- `product-service` — MongoDB, CRUD products (port 8083), exposes **/reserve** to atomically reduce stock

**Run**
```bash
mvn clean package -DskipTests
docker compose up --build
```
