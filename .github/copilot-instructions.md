# Copilot Coding Instructions

## General
- Use English for all identifiers and comments.
- Follow Clean Code and SOLID principles.
- Apply DRY (Don't Repeat Yourself) rules.

## Backend Java
- Java 21 + Spring Boot 4.x
- Use Maven for dependency management.
- Constructor injection for dependencies (avoid @Autowired).
- DTOs should be immutable; validate inputs in constructors.
- Avoid nulls; use Optional<T> appropriately.
- Logging: SLF4J + Logback; include context, avoid sensitive data.
- Layered architecture:
    - API → Domain → Data
    - No reverse dependencies between layers

## Naming Conventions
- Classes: PascalCase
- Methods/variables: camelCase
- Constants: UPPER_SNAKE_CASE
- Boolean methods/variables: use is/has prefix