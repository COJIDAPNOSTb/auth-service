# Auth Service

Full microservice on Spring Boot 3.0:
- Registration (`/auth/register`)
- Login + JWT auth (`/auth/login`)
- Logout (`/auth/logout`)
- 1 Secure endpoint (`/test/1`)

---

## Stack

- Java 17
- Spring Boot 3
- Spring Security
- JWT (JJWT)
- Redis
- PostgreSQL
- Docker + Docker Compose

---

## Quickstart

```bash
git clone https://github.com/your-repo/auth-service.git
cd auth-service
docker-compose up --build
