# Spring Learning

A hands-on learning repository for understanding **Spring Core, Spring Boot, and RESTful API development** through practical implementation.

This repository is intentionally built incrementally. Instead of learning Spring by only watching tutorials, each concept is introduced by writing code, testing it, documenting it, and committing the result.

The goal is to create a reference that is useful both for my future self and for other junior developers learning Spring.

---

## Current Progress

### Spring Core

* [x] Spring Boot application setup
* [x] IoC (Inversion of Control)
* [x] ApplicationContext
* [x] Spring Beans
* [x] POJO
* [x] `@Component`
* [x] `@Service`
* [x] `@Configuration`
* [x] `@Bean`
* [x] Dependency Injection
* [x] Constructor Injection
* [x] Component scanning
* [x] `CommandLineRunner` — introductory experiment

### Spring REST

* [x] `@RestController`
* [x] `@RequestMapping`
* [x] `@GetMapping`
* [x] `@PostMapping`
* [x] `@PutMapping`
* [x] `@DeleteMapping`
* [x] `@PathVariable`
* [x] `@RequestParam` — introductory concept
* [x] `@RequestBody`
* [x] DTOs
* [x] Java Records as DTOs
* [x] JSON request/response
* [x] Jackson
* [x] `ResponseEntity`
* [x] HTTP status codes
* [x] Controller/Service separation
* [x] Basic in-memory CRUD API

### Upcoming

* [ ] PATCH
* [ ] Validation
* [ ] Exception handling
* [ ] Global exception handling
* [ ] SQL fundamentals
* [ ] Relational database concepts
* [ ] JDBC
* [ ] JPA
* [ ] Hibernate
* [ ] Spring Data JPA
* [ ] Database-backed REST API
* [ ] Spring Security
* [ ] Authentication and Authorization
* [ ] Testing

---

# First REST API — Greeting CRUD

The first practical REST exercise is a simple Greeting API.

It does not use a database yet.

Data is temporarily stored in an in-memory `ArrayList`. This is intentional: the purpose of this exercise is to understand HTTP, REST, Spring MVC, controllers, services, DTOs, and dependency injection before introducing database persistence.

## Architecture

```text
HTTP Client
     │
     ▼
Controller
     │
     ▼
Service
     │
     ▼
In-Memory List
```

Eventually, the architecture will evolve into:

```text
HTTP Client
     │
     ▼
Controller
     │
     ▼
Service
     │
     ▼
Repository
     │
     ▼
JPA / Hibernate
     │
     ▼
JDBC
     │
     ▼
Database
```

---

# REST Endpoints

Base URL:

```text
http://localhost:8080/api/v1/greetings
```

## Create a Greeting

```http
POST /api/v1/greetings
```

Request:

```json
{
  "name": "Olu",
  "message": "Hello from Spring"
}
```

Response:

```http
201 Created
```

```json
{
  "id": 1,
  "name": "Olu",
  "message": "Hello from Spring"
}
```

---

## Get All Greetings

```http
GET /api/v1/greetings
```

Response:

```http
200 OK
```

Example:

```json
[
  {
    "id": 1,
    "name": "Olu",
    "message": "Hello from Spring"
  },
  {
    "id": 2,
    "name": "Ada",
    "message": "Welcome!"
  }
]
```

---

## Get a Greeting by ID

```http
GET /api/v1/greetings/{id}
```

Example:

```http
GET /api/v1/greetings/1
```

Response:

```http
200 OK
```

If the resource doesn't exist:

```http
404 Not Found
```

---

## Update a Greeting

```http
PUT /api/v1/greetings/{id}
```

Example:

```http
PUT /api/v1/greetings/1
```

Request:

```json
{
  "name": "Olu",
  "message": "Updated greeting"
}
```

Response:

```http
200 OK
```

---

## Delete a Greeting

```http
DELETE /api/v1/greetings/{id}
```

Example:

```http
DELETE /api/v1/greetings/1
```

Response:

```http
204 No Content
```

If the resource doesn't exist:

```http
404 Not Found
```

---

# Important Spring Concepts

## IoC — Inversion of Control

Normally, Java code can create and manage its own objects:

```java
GreetingService service = new GreetingService();
```

With Spring, the framework takes responsibility for creating and managing objects that are registered as beans.

```text
Application
     │
     ▼
Spring IoC Container
     │
     ├── GreetingService
     ├── GreetingController
     └── ...
```

This is the foundation of Spring.

---

## Dependency Injection

Instead of a class creating its dependencies:

```java
GreetingService service = new GreetingService();
```

the dependency is supplied to the class.

This project uses constructor injection:

```java
public GreetingController(GreetingService greetingService) {
    this.greetingService = greetingService;
}
```

Spring sees that `GreetingController` requires a `GreetingService` and injects the appropriate bean.

Constructor injection makes dependencies explicit and is generally preferred over field injection.

---

# Controller vs Service

The application separates HTTP concerns from application logic.

### Controller

The controller deals primarily with HTTP:

```text
Request
   ↓
Controller
   ↓
Service
   ↓
Response
```

Examples of controller responsibilities:

* Receiving HTTP requests
* Reading `@RequestBody`
* Reading `@PathVariable`
* Returning HTTP status codes
* Returning HTTP responses

### Service

The service contains the application logic.

For example:

```java
greetingService.create(request);
```

The controller doesn't need to know how the greeting is stored or created.

This separation makes the application easier to understand and evolve.

---

# DTOs

The REST API uses DTOs (Data Transfer Objects) to represent data entering and leaving the API.

### Request DTO

```java
public record GreetingRequest(
        String name,
        String message
) {
}
```

### Response DTO

```java
public record GreetingResponse(
        Long id,
        String name,
        String message
) {
}
```

These are Java records.

Records are useful for simple data-carrying objects because Java automatically provides functionality such as accessors, `equals()`, `hashCode()`, and `toString()`.

---

# `@RequestBody`

`@RequestBody` tells Spring to read data from the HTTP request body and convert it into a Java object.

For example:

```json
{
  "name": "Olu",
  "message": "Hello from Spring"
}
```

can be converted into:

```java
GreetingRequest
```

Spring Boot uses Jackson for JSON serialization and deserialization.

Conceptually:

```text
JSON
 ↓
Jackson
 ↓
Java Object
```

And when returning an object:

```text
Java Object
 ↓
Jackson
 ↓
JSON
```

---

# `@PathVariable`

A path variable identifies a resource in the URL.

Example:

```http
GET /api/v1/greetings/10
```

The `10` can be captured with:

```java
@GetMapping("/{id}")
public ResponseEntity<GreetingResponse> findById(
        @PathVariable Long id
) {
    ...
}
```

Use path variables when the value is part of the resource's identity.

---

# `@RequestParam`

Request parameters are commonly used for filtering, searching, sorting, and pagination.

Example:

```http
GET /api/v1/greetings?name=Olu
```

The value can be captured using:

```java
@RequestParam String name
```

Unlike a path variable, a request parameter generally modifies or filters the request rather than identifying the resource itself.

---

# `ResponseEntity`

`ResponseEntity` gives the controller explicit control over the HTTP response.

For example:

```java
return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(greeting);
```

This allows the application to communicate:

```text
HTTP Status
Headers
Body
```

Examples used in this project:

```text
200 OK
201 Created
204 No Content
404 Not Found
```

---

# HTTP Methods and CRUD

The API demonstrates the relationship between common HTTP methods and CRUD operations.

| HTTP Method | CRUD Operation | Purpose                     |
| ----------- | -------------- | --------------------------- |
| `POST`      | Create         | Create a resource           |
| `GET`       | Read           | Retrieve resources          |
| `PUT`       | Update         | Replace/update a resource   |
| `PATCH`     | Update         | Partially update a resource |
| `DELETE`    | Delete         | Remove a resource           |

PATCH is intentionally left for a later exercise.

---

# Testing

The API is tested using PowerShell's `Invoke-WebRequest`.

No Postman is required.

Example:

```powershell
$response = Invoke-WebRequest `
    -Uri "http://localhost:8080/api/v1/greetings" `
    -Method Post `
    -ContentType "application/json" `
    -Body '{"name":"Olu","message":"Hello from Spring"}'

$response.StatusCode
$response.Content
```

Expected:

```text
201
```

```json
{
  "id": 1,
  "name": "Olu",
  "message": "Hello from Spring"
}
```

PowerShell may throw a `WebException` when the server returns an HTTP error such as `404 Not Found`. This does not necessarily mean Spring Boot failed. It can simply mean the API correctly returned a non-2xx HTTP response.

---

# Current Limitation

The Greeting API currently stores data in an in-memory `ArrayList`.

Therefore:

```text
Application stops
      ↓
Memory is cleared
      ↓
All greetings are lost
```

This is intentional.

The next stages of the project will replace this temporary storage with persistent database storage.

---

# Learning Philosophy

This repository follows a progression from lower-level concepts to higher-level Spring abstractions.

```text
Java
  ↓
Spring Core
  ↓
Spring MVC / REST
  ↓
SQL
  ↓
JDBC
  ↓
JPA
  ↓
Hibernate
  ↓
Spring Data JPA
  ↓
Database-backed REST API
```

The goal is not simply to learn annotations.

The goal is to understand **what Spring is doing for us and what exists underneath the abstractions**.

---

# Git Milestones

Each major learning stage should result in a meaningful commit.

The first milestone represents:

```text
Spring Core fundamentals
+
Basic REST API
+
In-memory CRUD
```

Future commits will introduce additional concepts incrementally.
