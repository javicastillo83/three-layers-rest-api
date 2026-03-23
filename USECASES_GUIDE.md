# 📋 USE CASES - Casos de Uso Implementados

## ✅ Casos de Uso Creados (6 interfases + 6 implementaciones)

### Estructura
```
domain/usecase/
├── CreateUserUseCase.java (interfaz)
├── GetUserByIdUseCase.java (interfaz)
├── GetUserByUsernameUseCase.java (interfaz)
├── UpdateUserUseCase.java (interfaz)
├── DeactivateUserUseCase.java (interfaz)
├── DeleteUserUseCase.java (interfaz)
└── impl/
    ├── CreateUserUseCaseImpl.java (orquestación)
    ├── GetUserByIdUseCaseImpl.java (orquestación)
    ├── GetUserByUsernameUseCaseImpl.java (orquestación)
    ├── UpdateUserUseCaseImpl.java (orquestación)
    ├── DeactivateUserUseCaseImpl.java (orquestación)
    └── DeleteUserUseCaseImpl2.java (orquestación)
```

---

## 🎯 Qué Hace Cada Caso de Uso

### 1. **CreateUserUseCase**
```
Interfaz: CreateUserUseCase.java
Implementación: CreateUserUseCaseImpl.java

Responsabilidades:
✅ Orquesta la creación de usuario
✅ Delega a UserService.createUser()
✅ Valida entrada (delegado)
✅ Verifica unicidad de username (delegado)
✅ Registra en logs

Entrada: username, age
Salida: User (creado)
Excepciones: IllegalArgumentException, UserAlreadyExistsException
```

### 2. **GetUserByIdUseCase**
```
Interfaz: GetUserByIdUseCase.java
Implementación: GetUserByIdUseCaseImpl.java

Responsabilidades:
✅ Orquesta la búsqueda de usuario por ID
✅ Delega a UserService.getUserById()
✅ Registra en logs

Entrada: id (Long)
Salida: Optional<User>
Excepciones: Ninguna (retorna Optional)
```

### 3. **GetUserByUsernameUseCase**
```
Interfaz: GetUserByUsernameUseCase.java
Implementación: GetUserByUsernameUseCaseImpl.java

Responsabilidades:
✅ Orquesta la búsqueda de usuario por username
✅ Delega a UserService.getUserByUsername()
✅ Registra en logs

Entrada: username (String)
Salida: Optional<User>
Excepciones: Ninguna (retorna Optional)
```

### 4. **UpdateUserUseCase**
```
Interfaz: UpdateUserUseCase.java
Implementación: UpdateUserUseCaseImpl.java

Responsabilidades:
✅ Orquesta la actualización de usuario
✅ Delega a UserService.updateUser()
✅ Valida entrada (delegado)
✅ Verifica que usuario existe (delegado)
✅ Verifica unicidad de nuevo username (delegado)
✅ Registra en logs

Entrada: id, username, age
Salida: User (actualizado)
Excepciones: UserNotFoundException, UserAlreadyExistsException, IllegalArgumentException
```

### 5. **DeactivateUserUseCase**
```
Interfaz: DeactivateUserUseCase.java
Implementación: DeactivateUserUseCaseImpl.java

Responsabilidades:
✅ Orquesta la desactivación de usuario (soft delete)
✅ Delega a UserService.deactivateUser()
✅ Verifica que usuario existe (delegado)
✅ Marca usuario como inactivo (delegado)
✅ Registra en logs

Entrada: id (Long)
Salida: User (desactivado)
Excepciones: UserNotFoundException
```

### 6. **DeleteUserUseCase**
```
Interfaz: DeleteUserUseCase.java
Implementación: DeleteUserUseCaseImpl2.java

Responsabilidades:
✅ Orquesta la eliminación de usuario (hard delete)
✅ Delega a UserService.deleteUser()
✅ Verifica que usuario existe (delegado)
✅ Elimina usuario (delegado)
✅ Registra en logs

Entrada: id (Long)
Salida: void
Excepciones: UserNotFoundException
```

---

## 🏗️ Patrón de Diseño

### Orquestación (No Command/Query)

```java
@Component
public class CreateUserUseCaseImpl implements CreateUserUseCase {
    
    private final UserService userService;
    
    @Override
    public User execute(String username, Integer age) {
        log.info("Executing CreateUserUseCase...");
        
        // Orquesta: Delega al servicio
        User createdUser = userService.createUser(username, age);
        
        log.info("Use case completed");
        return createdUser;
    }
}
```

**Puntos clave:**
- ✅ Inyecta UserService
- ✅ Llama a métodos del servicio
- ✅ Orquesta la lógica
- ✅ Registro de logs
- ✅ Sin lógica compleja (es delegación)

---

## 📊 Flujo de Llamadas

```
Controller
    ↓ (delega)
UseCase (orquesta)
    ↓ (delega)
Service (lógica de negocio)
    ↓ (delega)
Repository (acceso a datos)
    ↓
Database
```

---

## 🧪 Tests Implementados

### CreateUserUseCaseImplTest
```
✅ shouldExecuteCreateUserUseCaseSuccessfully
   └─ Verifica que ejecuta correctamente

✅ shouldDelegateToServiceOnCreateUser
   └─ Verifica que delega al servicio (verify)
```

### GetUserByIdUseCaseImplTest
```
✅ shouldExecuteGetUserByIdUseCaseSuccessfully
   └─ Verifica que ejecuta correctamente

✅ shouldReturnEmptyOptionalWhenUserNotFound
   └─ Verifica manejo de Optional
```

**Nota:** Tests similares para otros casos de uso pueden crearse siguiendo el mismo patrón.

---

## 🎯 Responsabilidades de Cada Capa

```
┌─────────────────────────────────────────┐
│ Controller (Presentation)               │
│ ├─ Recibe solicitud HTTP                │
│ └─ Delega a UseCase                     │
└─────────────────────────────────────────┘
                  ↓ delega
┌─────────────────────────────────────────┐
│ UseCase (Domain - Orquestación)         │
│ ├─ Orquesta el flujo                    │
│ ├─ Llama a servicios                    │
│ └─ Registra en logs                     │
└─────────────────────────────────────────┘
                  ↓ delega
┌─────────────────────────────────────────┐
│ Service (Domain - Lógica de Negocio)    │
│ ├─ Implementa reglas de negocio         │
│ ├─ Valida datos                         │
│ └─ Delega a repository                  │
└─────────────────────────────────────────┘
                  ↓ delega
┌─────────────────────────────────────────┐
│ Repository (Data - Persistencia)        │
│ ├─ Accede a base de datos               │
│ └─ Convierte entidades                  │
└─────────────────────────────────────────┘
```

---

## 📝 Patrón de Interfaz + Implementación

```java
// Interfaz (contrato)
public interface CreateUserUseCase {
    User execute(String username, Integer age);
}

// Implementación (orquestación)
@Component
public class CreateUserUseCaseImpl implements CreateUserUseCase {
    // Inyectar servicios necesarios
    // Implementar lógica de orquestación
}
```

**Ventajas:**
- ✅ Desacoplamiento: Controller depende de interfaz, no de implementación
- ✅ Testeable: Fácil de mockar en tests
- ✅ Reemplazable: Puedo cambiar implementación sin afectar controller
- ✅ Claro: Define contrato de operación

---

## 🚀 Cómo Usar en Controller

```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private final CreateUserUseCase createUserUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeactivateUserUseCase deactivateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    
    public UserController(
            CreateUserUseCase createUserUseCase,
            GetUserByIdUseCase getUserByIdUseCase,
            // ... otros casos de uso
    ) {
        this.createUserUseCase = createUserUseCase;
        this.getUserByIdUseCase = getUserByIdUseCase;
        // ...
    }
    
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserRequest request) {
        // Delega al caso de uso
        User user = createUserUseCase.execute(request.getUsername(), request.getAge());
        return ResponseEntity.status(HttpStatus.CREATED).body(mapToResponse(user));
    }
}
```

---

## ✨ Beneficios de Esta Estructura

✅ **Orquestación clara**: Casos de uso orquestan servicios
✅ **Responsabilidades separadas**: Cada capa tiene su responsabilidad
✅ **Desacoplamiento**: Controller → UseCase → Service → Repository
✅ **Testeable**: Fácil de mockear y testear
✅ **Mantenible**: Código limpio y organizado
✅ **Escalable**: Fácil agregar nuevos casos de uso

---

## 📊 Resumen

| Caso de Uso | Interfaz | Implementación | Tests |
|------------|----------|----------------|-------|
| Create | ✅ | ✅ | ✅ |
| GetById | ✅ | ✅ | ✅ |
| GetByUsername | ✅ | ✅ | Pendiente |
| Update | ✅ | ✅ | Pendiente |
| Deactivate | ✅ | ✅ | Pendiente |
| Delete | ✅ | ✅ | Pendiente |

**Total: 6 interfases + 6 implementaciones + 2 test classes (extensibles)**

