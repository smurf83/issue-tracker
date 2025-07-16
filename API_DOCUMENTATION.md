# Issue Tracker API Documentation

## Overview
The Issue Tracker is a RESTful API built with Spring Boot 3.x for managing software issues and bugs. It provides user authentication, role-based access control, and complete CRUD operations for issue management.

## Technologies Used
- Java 17
- Spring Boot 3.2.0
- Spring Security with JWT
- Spring Data JPA
- H2 Database (for development)
- PostgreSQL (for production)
- Swagger/OpenAPI 3
- Lombok
- MapStruct

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+

### Running the Application
1. Clone the repository
2. Navigate to the project directory
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```
4. The application will start on http://localhost:8080

### Database Access
- H2 Console: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:testdb`
  - Username: `sa`
  - Password: `password`

### API Documentation
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

## Default Users
The application seeds three default users on startup:

| Email | Password | Role |
|-------|----------|------|
| admin@example.com | admin | ADMIN |
| dev@example.com | dev | DEVELOPER |
| tester@example.com | tester | TESTER |

## Authentication

### Register a New User
```http
POST /auth/register
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123",
  "role": "TESTER"
}
```

### Login
```http
POST /auth/login
Content-Type: application/json

{
  "email": "admin@example.com",
  "password": "admin"
}
```

Response:
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "email": "admin@example.com",
  "role": "ADMIN",
  "message": "Login successful"
}
```

## Issue Management

### Create an Issue (TESTER, ADMIN only)
```http
POST /issues
Authorization: Bearer <jwt_token>
Content-Type: application/json

{
  "title": "Bug in login functionality",
  "description": "Users cannot login with valid credentials",
  "priority": "HIGH"
}
```

### Get All Issues (with filtering and pagination)
```http
GET /issues?status=OPEN&priority=HIGH&page=0&size=10&sortBy=createdAt&sortDir=desc
Authorization: Bearer <jwt_token>
```

### Get Issue by ID
```http
GET /issues/{id}
Authorization: Bearer <jwt_token>
```

### Update an Issue (ADMIN, DEVELOPER only)
```http
PUT /issues/{id}
Authorization: Bearer <jwt_token>
Content-Type: application/json

{
  "title": "Updated title",
  "description": "Updated description",
  "status": "IN_PROGRESS",
  "priority": "MEDIUM",
  "assignedToId": 2
}
```

### Delete an Issue (ADMIN only)
```http
DELETE /issues/{id}
Authorization: Bearer <jwt_token>
```

## Role-Based Access Control

### ADMIN
- Can perform all operations
- Can delete issues
- Can assign/reassign issues
- Can change issue status

### DEVELOPER
- Can view all issues
- Can update issues (change status, assign to users)
- Cannot delete issues
- Cannot create issues

### TESTER
- Can view all issues
- Can create new issues
- Cannot delete issues
- Cannot assign issues or change status

## Error Handling
The API returns consistent error responses:

```json
{
  "message": "Validation failed",
  "details": ["Email is required", "Password must be at least 6 characters"],
  "timestamp": "2023-12-01T10:30:00",
  "path": "/auth/register"
}
```

## Status Codes
- 200: Success
- 201: Created
- 400: Bad Request
- 401: Unauthorized
- 403: Forbidden
- 404: Not Found
- 500: Internal Server Error

## Issue Status Workflow
1. **OPEN** - Newly created issue
2. **IN_PROGRESS** - Issue is being worked on
3. **RESOLVED** - Issue has been fixed

## Issue Priorities
- **LOW** - Minor issues
- **MEDIUM** - Standard issues
- **HIGH** - Critical issues

## Pagination and Sorting
All list endpoints support pagination and sorting:
- `page`: Page number (0-based, default: 0)
- `size`: Page size (default: 10)
- `sortBy`: Field to sort by (default: "createdAt")
- `sortDir`: Sort direction - "asc" or "desc" (default: "desc")

## Development Notes
- The application uses H2 in-memory database for development
- JWT tokens expire after 24 hours
- All timestamps are in UTC
- Swagger UI is available for interactive API testing