# User CRUD API

## Descripción
CRUD completo para la gestión de usuarios (User) con username y edad, implementando arquitectura de 3 capas siguiendo los estándares del equipo.

## Estructura del Proyecto

### Capas de la Aplicación

#### 1. API Layer (Presentation)
- **Location**: `com.carusato.restapi.presentation`
- **Components**:
  - `UserController`: REST controller que maneja las peticiones HTTP
  - DTOs: `CreateUserRequest`, `UpdateUserRequest`, `UserResponse`
  - `GlobalExceptionHandler`: Manejo centralizado de excepciones

#### 2. Domain Layer (Business Logic)
- **Location**: `com.carusato.restapi.domain` y `com.carusato.restapi.application`
- **Components**:
  - `User`: Entidad POJO del dominio (sin anotaciones ORM)
  - `UserService`: Servicio que implementa la lógica de negocio
  - `UserRepository`: Interfaz de repositorio (abstracción)
  - Excepciones de negocio: `UserNotFoundException`, `UserAlreadyExistsException`

#### 3. Data Layer (Persistence)
- **Location**: `com.carusato.restapi.infrastructure.persistence`
- **Components**:
  - `UserEntity`: Entidad JPA para mapeo con base de datos
  - `UserRepositoryImpl`: Implementación del repositorio
  - `UserJpaRepository`: Spring Data JPA repository
  - `UserEntityMapper`: Mapear entre capas

## Endpoints REST

### Create User
```
POST /api/users
Content-Type: application/json

{
  "username": "john_doe",
  "age": 30
}

Response: 201 Created
{
  "id": 1,
  "username": "john_doe",
  "age": 30,
  "active": true
}
```

### Get User by ID
```
GET /api/users/{id}

Response: 200 OK
{
  "id": 1,
  "username": "john_doe",
  "age": 30,
  "active": true
}
```

### Update User
```
PUT /api/users/{id}
Content-Type: application/json

{
  "username": "jane_doe",
  "age": 35
}

Response: 200 OK
{
  "id": 1,
  "username": "jane_doe",
  "age": 35,
  "active": true
}
```

### Deactivate User
```
PUT /api/users/{id}/deactivate

Response: 200 OK
{
  "id": 1,
  "username": "john_doe",
  "age": 30,
  "active": false
}
```

### Delete User
```
DELETE /api/users/{id}

Response: 204 No Content
```

## Validaciones y Reglas de Negocio

### Validaciones de Entrada
- **Username**: No puede ser nulo o vacío
- **Age**: Debe estar entre 0 y 150

### Reglas de Negocio
- Username debe ser único
- Al crear un usuario, por defecto está activo (`active: true`)
- No se permite cambiar el username a uno que ya existe
- Al deactivar un usuario, se mantienen sus datos

## Tests

### Ejecución de Tests
```bash
mvn test
```

### Cobertura de Tests
Los tests unitarios de `UserService` cubren:
- ✅ Crear usuario exitosamente
- ✅ Excepciones de validación (username en blanco, edad inválida)
- ✅ Excepciones de negocio (usuario duplicado, no encontrado)
- ✅ Recuperar usuario por ID y por username
- ✅ Actualizar usuario
- ✅ Deactivar usuario
- ✅ Eliminar usuario

### Principios de Testing
- **TDD**: Tests escritos junto con el código de producción
- **FIRST**: Rápidos, aislados, repetibles, auto-verificables, oportunos
- **Mockito**: Para mockear dependencias
- **AssertJ**: Para aserciones fluidas

Ejemplo:
```java
@Test
@DisplayName("Should create user successfully with valid data")
void shouldCreateUserSuccessfully() {
    // Arrange
    String username = "john_doe";
    Integer age = 30;
    
    when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
    when(userRepository.save(any(User.class))).thenReturn(expectedUser);
    
    // Act
    User createdUser = userService.createUser(username, age);
    
    // Assert
    assertThat(createdUser)
            .isNotNull()
            .extracting(User::getId, User::getUsername, User::getAge, User::isActive)
            .containsExactly(1L, username, age, true);
}
```

## Configuración de Base de Datos

### Requisitos
- MySQL 8.0+

### Script de Creación
```sql
CREATE DATABASE rest_api_db;
USE rest_api_db;

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    age INT NOT NULL,
    active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Propiedades de Configuración
Se definen en `application.yaml`:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/rest_api_db
    username: root
    password: root
  jpa:
    hibernate:
      ddl-auto: update
```

## Manejo de Excepciones

### Excepciones de Negocio
- **UserNotFoundException** (404): Usuario no encontrado
- **UserAlreadyExistsException** (409): Username ya existe

### Excepciones de Validación
- **IllegalArgumentException** (400): Datos inválidos

Ejemplo de respuesta de error:
```json
{
  "error": "Not Found",
  "message": "User not found with ID: 999",
  "status": 404,
  "timestamp": "2024-03-23T10:30:45.123"
}
```

## Buenas Prácticas Implementadas

### Arquitectura
✅ Separación clara de responsabilidades (3 capas)  
✅ Inversión de dependencias (interfaces)  
✅ Mapeo entre capas con mappers  

### Código
✅ Inmutabilidad en entidades de dominio  
✅ POJOs sin anotaciones ORM en dominio  
✅ Constructor injection  
✅ Validación en constructores de DTOs  
✅ Logging con SLF4J y @Slf4j  
✅ Sin uso de `var` keyword  

### Testing
✅ TDD (Test-Driven Development)  
✅ Mockito para mocks  
✅ AssertJ para aserciones  
✅ Prueba de CRUD completo  
✅ Cobertura de casos de error  

## Próximos Pasos

- [ ] Implementar MapStruct para mapeo automático entre DTOs y entities
- [ ] Agregar paginación y filtrado
- [ ] Implementar auditoría (created_at, updated_at)
- [ ] Agregar autenticación y autorización
- [ ] Implementar transacciones con @Transactional
- [ ] Agregar más campos y validaciones complejas

