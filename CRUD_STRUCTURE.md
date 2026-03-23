# CRUD User - Estructura de Archivos Creados

## 📁 Árbol de Directorios

```
src/
├── main/
│   └── java/com/carusato/restapi/
│       ├── domain/
│       │   ├── User.java (POJO - Entidad de dominio)
│       │   ├── exception/
│       │   │   ├── UserNotFoundException.java
│       │   │   └── UserAlreadyExistsException.java
│       │   └── repository/
│       │       └── UserRepository.java (Interfaz)
│       │
│       ├── application/
│       │   └── service/
│       │       └── UserService.java (Lógica de negocio)
│       │
│       ├── presentation/
│       │   ├── dto/
│       │   │   ├── CreateUserRequest.java
│       │   │   ├── UpdateUserRequest.java
│       │   │   └── UserResponse.java
│       │   ├── controller/
│       │   │   └── UserController.java (REST endpoints)
│       │   └── exception/
│       │       └── GlobalExceptionHandler.java (@ControllerAdvice)
│       │
│       └── infrastructure/
│           └── persistence/
│               ├── entity/
│               │   └── UserEntity.java (JPA Entity)
│               ├── mapper/
│               │   └── UserEntityMapper.java
│               └── repository/
│                   ├── UserJpaRepository.java (Spring Data JPA)
│                   └── UserRepositoryImpl.java (Implementación)
│
└── test/
    └── java/com/carusato/restapi/
        └── application/service/
            └── UserServiceTest.java (Unit Tests - Mockito + AssertJ)
```

## 📋 Archivos Creados (12 archivos)

### Domain Layer (4 archivos)
1. **User.java** - POJO inmutable con builder pattern
2. **UserRepository.java** - Interfaz de repositorio
3. **UserNotFoundException.java** - Excepción de negocio
4. **UserAlreadyExistsException.java** - Excepción de negocio

### Application Layer (1 archivo)
5. **UserService.java** - Servicios con lógica de negocio (CRUD completo)

### Presentation Layer (4 archivos)
6. **CreateUserRequest.java** - DTO con validación en constructor
7. **UpdateUserRequest.java** - DTO con validación en constructor
8. **UserResponse.java** - DTO de respuesta
9. **UserController.java** - REST Controller (todos los endpoints)
10. **GlobalExceptionHandler.java** - Manejo centralizado de excepciones

### Infrastructure Layer (2 archivos)
11. **UserEntity.java** - Entidad JPA
12. **UserJpaRepository.java** - Spring Data JPA repository
13. **UserRepositoryImpl.java** - Implementación del repositorio
14. **UserEntityMapper.java** - Mapeo entre capas

### Tests (1 archivo)
15. **UserServiceTest.java** - Tests con Mockito y AssertJ

## 🔄 CRUD Endpoints Implementados

| Método | Endpoint | Descripción | Status |
|--------|----------|-------------|--------|
| POST | `/api/users` | Crear usuario | 201 Created |
| GET | `/api/users/{id}` | Obtener usuario por ID | 200 OK / 404 |
| PUT | `/api/users/{id}` | Actualizar usuario | 200 OK / 404 |
| PUT | `/api/users/{id}/deactivate` | Desactivar usuario | 200 OK / 404 |
| DELETE | `/api/users/{id}` | Eliminar usuario | 204 No Content / 404 |

## 📦 Dependencias Agregadas a pom.xml

```xml
<!-- Data Access -->
<spring-boot-starter-data-jpa>
<spring-boot-starter-validation>

<!-- Object Mapping -->
<mapstruct> (1.5.5.Final)
<mapstruct-processor>

<!-- Testing -->
<mockito-core>
<mockito-junit-jupiter>
<assertj-core>
```

## ✨ Características Implementadas

### Arquitectura
✅ Separación de 3 capas (API, Domain, Data)  
✅ POJOs sin anotaciones ORM en dominio  
✅ Mapeo entre capas (Entity ↔ Domain)  
✅ Inversión de dependencias (interfaces)  

### Validaciones
✅ DTOs con validación en constructor  
✅ JSR303/Jakarta Validation annotations  
✅ Excepciones de negocio específicas  
✅ Manejo centralizado de excepciones  

### Código
✅ Entidades inmutables (final fields)  
✅ Builder pattern para objetos complejos  
✅ Constructor injection (Spring)  
✅ Logging con @Slf4j (SLF4J/Logback)  
✅ Tipos explícitos (sin var keyword)  
✅ JavaDoc en métodos públicos  

### Testing
✅ Tests con Mockito (mocks de dependencias)  
✅ Aserciones con AssertJ (fluidas)  
✅ Cobertura de CRUD completo  
✅ Casos de éxito y error  
✅ FIRST principle (Fast, Isolated, Repeatable, Self-verifying, Timely)  
✅ TDD (Test-Driven Development)  

### REST
✅ Plural nouns para recursos  
✅ HTTP methods correcto (GET, POST, PUT, DELETE)  
✅ Status codes apropiados  
✅ DTOs para request/response  
✅ Path parameters para IDs  

## 🚀 Próximos Pasos

1. **Configurar base de datos MySQL**
   - Crear base de datos: `rest_api_db`
   - Ejecutar `mvn spring-boot:run`

2. **Mejorar mapeo**
   - Usar MapStruct para automatizar mapeo entre DTOs y entities

3. **Agregar funcionalidades**
   - Paginación y filtrado
   - Auditoría (created_at, updated_at)
   - Búsqueda por username
   - Transacciones (@Transactional)

4. **Seguridad**
   - Autenticación (JWT)
   - Autorización (roles)

## 📚 Documentación Relacionada

- Ver `USER_CRUD_README.md` para guía de uso
- Ver `.github/copilot-instructions.md` para estándares del equipo

