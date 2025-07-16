# issue-tracker
A Restful API app created with Cursor AI

Build a complete Spring Boot 3.x application in Java 17 called "Issue Tracker".

## Goal
This is a backend REST API for reporting and managing software issues or bugs.

## Technologies
- Java 17
- Spring Boot (3.x)
- Spring Web
- Spring Data JPA
- Spring Security with JWT
- PostgreSQL (use H2 in-memory DB for dev)
- Lombok
- Swagger / OpenAPI for documentation
- MapStruct (optional for DTO mapping)

## Features
### 1. User Management
- Entities: `User`, `Role`
- Roles: ADMIN, DEVELOPER, TESTER
- Endpoints:
  - `POST /auth/register` → Register new user
  - `POST /auth/login` → Return JWT
- Password hashing (BCrypt)
- Role-based access control via annotations

### 2. Issue Management
- Entity: `Issue`
- Fields: `id`, `title`, `description`, `status`, `priority`, `createdBy`, `assignedTo`, `createdAt`, `updatedAt`
- Enum for status: OPEN, IN_PROGRESS, RESOLVED
- Enum for priority: LOW, MEDIUM, HIGH
- Endpoints:
  - `POST /issues` → Create a new issue (ROLE_TESTER, ROLE_ADMIN)
  - `GET /issues` → List issues (with pagination + filter by status/assignee/priority)
  - `GET /issues/{id}` → Get single issue
  - `PUT /issues/{id}` → Update title/desc/assignedTo/status
  - `DELETE /issues/{id}` → Delete (ROLE_ADMIN only)

### 3. Security
- Implement JWT-based authentication
- Protect issue endpoints
- Only ADMINs can delete issues
- Only ADMIN/DEVELOPER can assign or change status
- TESTER can only create issues

### 4. Documentation
- Auto-generate Swagger UI
- Include all endpoint docs

### 5. Seed Data
- Add a CommandLineRunner that seeds 3 users:
  - admin@example.com / admin / ADMIN
  - dev@example.com / dev / DEVELOPER
  - tester@example.com / tester / TESTER

### 6. Extra (Optional)
- Add @ControllerAdvice for global error handling
- Add timestamps with @CreatedDate/@LastModifiedDate
- Return error messages in consistent JSON format

Please structure the project using the following packages:
- `controller`
- `service`
- `repository`
- `model`
- `dto`
- `config`
- `exception`
- `security`

Use Lombok where possible to reduce boilerplate. Use DTOs to avoid exposing entities directly. Use Java records for DTOs if appropriate. Make sure Swagger UI works out of the box on local dev (e.g., `localhost:8080/swagger-ui.html`).

