# School Management System (Spring Boot)

Package: `com.aman.school`

This project is a Spring Boot skeleton implementing a School Management System with JWT authentication and MySQL.

## Quick start
1. Create database: `CREATE DATABASE schooldb;`
2. Update `src/main/resources/application.properties` with your DB credentials and a strong JWT secret.
3. Build & run:
   ```bash
   mvn clean package
   mvn spring-boot:run
   ```

## Auth endpoints
- `POST /api/auth/register` {"username":"user","password":"pass"}
- `POST /api/auth/login` {"username":"user","password":"pass"}

Include header `Authorization: Bearer <token>` for protected endpoints.
