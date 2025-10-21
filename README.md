# JobLync Talent Management System

## Overview

JobLync is a comprehensive talent management system built with Spring Boot. This system handles various HR functions including job postings, applications, employee management, performance reviews, and more.

## Prerequisites

- Java 21
- Maven 3.6+
- PostgreSQL

## Database Setup

Follow the instructions in [DATABASE_SETUP.md](DATABASE_SETUP.md) to set up PostgreSQL with the required database and user.

## Running the Application

1. Make sure you have Java 21 and Maven installed
2. Set up PostgreSQL as described in [DATABASE_SETUP.md](DATABASE_SETUP.md)
3. Run the application:
   ```
   mvn spring-boot:run
   ```

The application will start on port 8080.

## Testing Authentication Endpoints

### User Registration

To register a new user, send a POST request to:
```
POST http://localhost:8080/api/auth/register
Content-Type: application/json

{
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "08031234567",
  "email": "john.doe@example.com",
  "password": "your_password",
  "confirmPassword": "your_password",
  "role": "APPLICANT"
}
```

If you don't provide a password, the system will generate a temporary one and try to email it to you. If you don't provide a role, it will default to APPLICANT.

### User Login

To login, send a POST request to:
```
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
  "email": "john.doe@example.com",
  "password": "your_password"
}
```

On successful login, you'll receive a JWT token that you can use for authenticated requests.

## Available Roles

- APPLICANT: For job applicants
- EMPLOYEE: For regular employees
- HR_MANAGER: For HR managers
- ADMIN: For system administrators

## API Documentation

The application exposes RESTful APIs for various HR functions:
- Authentication (registration, login)
- User management
- Job postings
- Job applications
- Performance reviews
- Career development plans
- Learning modules
- And more

## JWT Token Generation

JWT tokens are generated using:
- Secret key: A 512-bit secret defined in the configuration
- Algorithm: HS512
- Expiration: 24 hours (86400000 milliseconds)
- Issuer: JobLync
- Audience: JobLyncUsers

## Email Configuration

The application is configured to use localhost for email sending in development mode. In production, you should update the email configuration in [application.properties](file:///C:/Users/DELL/Desktop/JobLync/src/main/resources/application.properties#L1-L29) with your actual email server settings.