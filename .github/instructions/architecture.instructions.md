---
applyTo: '**'
description: 'Backend architecture, layering, and API rules'
---
# Project Architecture

- Three-layer pattern: API → Domain → Data
- API layer: controllers, DTOs (immutable), MapStruct mappers
- Domain layer: services, business logic, use cases
- Data layer: repositories, persistence, entities (JPA)