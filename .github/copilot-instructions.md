# Team Coding Standards

## General
- Always use English for identifiers and comments.
- Follow Clean Code principles.

## Backend (Java)
- Version: Java 21.
- Framework: Spring Boot 4.0.3
- Build Tool: Maven.
- Prefer constructor injection over `@Autowired`.

### Code Principles
- Follow **SOLID** principles (Single Responsibility, Open/Closed, Liskov Substitution, Interface Segregation, Dependency Inversion).
- Follow **Clean Code** practices (meaningful names, small methods, no code duplication).
- Apply **DRY** (Don't Repeat Yourself) principle - extract common logic into reusable methods.
- Use descriptive English names for all identifiers and comments.
- **Avoid using `var` keyword** for better code readability - use explicit types in all declarations.

### Naming Conventions
- **Classes**: PascalCase (`UserService`, `CreateUserRequest`, `UserRepository`)
- **Methods/Variables**: camelCase (`getUserById`, `userName`, `isActive`)
- **Constants**: UPPER_SNAKE_CASE (`MAX_USERS`, `DEFAULT_PAGE_SIZE`, `API_VERSION`)
- **Package names**: lowercase (`com.carusato.restapi.domain`)
- **Boolean methods/variables**: use is/has prefix (`isActive`, `hasPermission`, `isValid`)

### Null Safety and Optional
- **Avoid null references**; use `Optional<T>` for potentially missing values.
- Use `Optional.ofNullable()` when handling potentially null values.
- **Avoid `Optional.get()` without checking `isPresent()` first**.
- Prefer `Optional.orElse()`, `orElseThrow()`, or `orElseGet()`.
- **Never use Optional for method parameters**; use method overloading instead.
- Initialize collections as empty instead of null: `Collections.emptyList()`.

### Logging Standards
- Use **SLF4J** with **Logback** as logging framework (included via Spring Boot).
- Use `@Slf4j` annotation for automatic logger injection.
- Use appropriate log levels:
  - **DEBUG**: Development and detailed tracing (disabled in production)
  - **INFO**: Important business events (application start, user actions)
  - **WARN**: Warning conditions (deprecated features, recoverable errors)
  - **ERROR**: Error conditions (exceptions, failed operations)
- Include meaningful context in log messages (user ID, operation, resource ID, etc.).
- **Never log sensitive data**: passwords, tokens, personal information, credit cards.
- Use structured logging for better analysis and monitoring.

### Documentation
- Document all public methods with JavaDoc comments.
- Include method description, parameters (`@param`), return value (`@return`), and exceptions (`@throws`).
- Keep documentation clear and concise, following Clean Code principles.
- Avoid obvious comments; focus on WHY not WHAT.
- Use comments to explain business logic and non-obvious decisions.
- Use `TODO`/`FIXME` for future improvements with explanation.

### Dependency Management
- Use **Maven** as the build tool and dependency manager for Java projects.
- **Inherit dependency versions from Spring Boot parent** (`spring-boot-starter-parent`):
  - Use Spring Boot parent for Maven configuration and transitive dependency management.
  - Spring Boot parent provides version management for common dependencies (Spring, JUnit, Mockito, AssertJ, etc.).
  - Only specify versions for project-specific dependencies not managed by Spring Boot parent.
- **Define project-specific dependency versions as Maven properties**:
  - Place version variables in `<properties>` section at the top of pom.xml.
  - Use naming convention: `groupId-artifactId.version` (e.g., `mapstruct.version`, `springdoc-openapi.version`).
  - Reference properties using `${property.name}` syntax.
  - **Example**:
    ```xml
    <properties>
        <java.version>21</java.version>
        <mapstruct.version>1.5.5.Final</mapstruct.version>
        <springdoc-openapi.version>2.6.0</springdoc-openapi.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.mapstruct</groupId>
            <artifactId>mapstruct</artifactId>
            <version>${mapstruct.version}</version>
        </dependency>
    </dependencies>
    ```
- Keep pom.xml organized with clear sections: properties, dependencies, build plugins.
- Use only verified and stable versions from Maven Central Repository.
- Use **MapStruct** for object mapping between layers (DTO ↔ Entity).
- Use **JSR303/Jakarta Validation** for field validation in DTOs and entities.

### Collections Best Practices
- Use specific types: `List`, `Set`, `Map` (never raw types).
- Use immutable collections when possible: `Collections.unmodifiableList()`.
- Initialize with appropriate capacity for performance.
- Use forEach or Stream API instead of traditional for loops (when appropriate).
- Avoid null collections; use empty collections: `Collections.emptyList()`.
- Avoid nested streams; use `flatMap()` instead.

### Lombok Best Practices
- Use `@Getter` and `@Setter` for data classes (DTOs, entities).
- Use `@NoArgsConstructor` and `@AllArgsConstructor` for immutable objects.
- Use `@Builder` for objects with many constructor parameters.
- Use `@Slf4j` for automatic logger injection.
- **Avoid `@Data`** (too much auto-generated code; use specific annotations).
- Exclude fields from equals/hashCode with `@EqualsAndHashCode.Exclude`.

### Date/Time Standards
- **Always use `java.time.LocalDateTime` or `java.time.ZonedDateTime`** (never `java.util.Date`).
- Use ISO-8601 format for serialization: `yyyy-MM-dd'T'HH:mm:ss'Z'`
- Store dates in UTC in database.
- Use `@JsonFormat` annotation on date fields for consistency.

### Constants Management
- Define constants in dedicated `Constants` class or interface.
- Group related constants in separate classes: `UserConstants`, `OrderConstants`, etc.
- Use UPPER_SNAKE_CASE naming for constants.
- Document purpose of constants with comments.
- **Avoid magic numbers/strings in code**.

### Layer Dependency Rules
- **API Layer** depends on: Domain Layer (Services, Entities, DTOs)
- **Domain Layer** depends on: NO external frameworks (Spring, JPA annotations on entities)
- **Data Layer** depends on: Domain Layer (Repository interfaces, Entities)
- **Flow**: API → Domain → Data (unidirectional, never reverse)
- DTOs are the boundary between API Layer and Domain Layer.
- MapStruct mappers transform: API DTOs ↔ Domain Entities

### Exception Handling
- Create specific business exceptions extending `RuntimeException`.
- Place custom exceptions in `domain/exception` package.
- Use meaningful exception names: `UserAlreadyExistsException`, `UserNotFoundException`, etc.
- Include clear error messages with context information.
- Use `@ControllerAdvice` for global exception handling in API Layer.

### DTO Validation
- Use **JSR303/Jakarta Validation** annotations (`@NotNull`, `@NotBlank`, `@Email`, `@Pattern`, etc.) for documentation and external validation tools.
- **Validate DTO inputs in the constructor** since DTOs should be immutable after creation.
- Place validation annotations on DTO fields for clarity.
- **Validation logic must be in constructor**, not dependent on Spring framework (`@Valid`, `@Validated`).
- Constructor should throw validation exceptions (e.g., `IllegalArgumentException`) if data is invalid.
- Include custom validation messages for better error reporting.

### Immutability Guidelines
- Domain entities should be **immutable when possible**.
- Use `final` fields and avoid setters in domain entities.
- For mutable operations, use the builder pattern or factory methods.
- Consider using `record` classes for value objects in Java 21+.
- Data Layer entities (JPA) may have setters (required by JPA).
- DTOs in API Layer can use `@Getter` and `@Setter` via Lombok for convenience.

### Testing Standards
- Use **TDD** (Test-Driven Development) approach: write tests before implementation.
- Use **Mockito** for mocking dependencies in unit tests.
- **Prefer `@InjectMocks` over manual mock creation in `@Before`**:
  - Use `@Mock` to annotate mock dependencies.
  - Use `@InjectMocks` to automatically inject mocks into the class under test.
  - Mark test class with `@ExtendWith(MockitoExtension.class)`.
  - Avoid manual instantiation in `@BeforeEach` - let Mockito handle it.
  - **Example**:
    ```java
    @ExtendWith(MockitoExtension.class)
    class UserServiceTest {
        @Mock
        private UserRepository userRepository;
        
        @InjectMocks
        private UserService userService;
        
        // No @Before needed - Mockito injects automatically
    }
    ```
- Use **AssertJ** for fluent assertions in tests.
- Follow **FIRST** principle for tests:
  - **F**ast: Tests should run quickly.
  - **I**solated: Tests should be independent of each other.
  - **R**epeatable: Tests should produce consistent results.
  - **S**elf-verifying: Tests should have clear pass/fail outcomes.
  - **T**imely: Write tests alongside production code.

### Code Coverage Standards
- Use **JaCoCo** for code coverage metrics.
- Maintain **minimum 85% code coverage** across all modules.
- Code coverage target: `target/site/jacoco/index.html` after `mvn test jacoco:report`.
- Use **PiTest** (Mutation Testing) for test quality assurance.
- Ensure **Mutation Coverage > 80%** via PiTest.
- Run `mvn org.pitest:pitest-maven:mutationCoverage` to generate mutation reports.
- Address low mutation scores by improving test assertions and edge case coverage.
- PiTest report location: `target/pit-reports/`

### Stream API Guidelines
- Use streams for functional operations (map, filter, reduce).
- Keep stream chains readable (break into multiple lines if needed).
- Avoid nested streams; use `flatMap()` instead.
- Be careful with side effects in streams (prefer returning new values).

### Architecture: Three-Layer Pattern
Backend follows a **three-layer architecture**:

1. **API Layer** (`presentation/controller`)
   - Handles HTTP requests and responses.
   - Controllers receive requests and delegate to services.
   - Contains DTOs (Data Transfer Objects) for request/response mapping.
   - **DTOs should be immutable**: use `@Getter`, `final` fields, and validate in constructor.
   - Use JSR303/Jakarta Validation annotations on DTO fields (for documentation).
   - **Constructor validates all inputs**: throw exceptions if data is invalid.
   - Uses `@RestController`, `@RequestMapping`, etc.
   - MapStruct mappers convert API DTOs to/from Domain entities.
   - Never expose domain entities directly; always use DTOs.

2. **Domain Layer** (`application/service`, `domain`, `domain/usecase`)
   - Contains business logic and domain models.
   - **Domain Entities**: Use POJOs (Plain Old Java Objects) without ORM annotations.
   - **Services**: Orchestrate business operations and implement core logic.
     - Services handle validations, business rules, and exception throwing.
     - Services delegate data access to repositories.
   - **Use Cases**: Define specific business operations that orchestrate services.
     - Use Cases follow interface + implementation pattern (`interface` + `Impl` class).
     - Use Cases inject and call services (not direct repository access).
     - Use Cases coordinate complex workflows (e.g., CreateUserUseCase → UserService).
     - Use Cases register operations in logs.
     - Example: `CreateUserUseCase` → calls `UserService.createUser()`.
   - Independent of external frameworks (NO Spring, JPA annotations on entities).
   - No dependency on any other layer (except within Domain Layer).
   - Throws custom business exceptions.
   - **Architecture flow**: Controller → UseCase → Service → Repository

3. **Data Layer** (`infrastructure/persistence`, `domain/repository`)
   - Implements data access and persistence.
   - Repository interfaces defined in Domain Layer.
   - Implementation handles database operations using JPA/Hibernate.
   - Mappers convert Domain POJOs ↔ JPA Entities.
   - Uses JPA entities with `@Entity`, `@Table`, etc.
   - Depends only on Domain Layer interfaces.

### REST API Conventions
- Use **plural nouns** for resources: `/api/users`, `/api/products` (not `/user`, `/product`).
- HTTP Methods:
  - `GET`: Retrieve resource(s)
  - `POST`: Create new resource
  - `PUT`: Full update of resource
  - `PATCH`: Partial update of resource
  - `DELETE`: Delete resource
- Use path parameters for IDs: `GET /api/users/{id}`
- Use query parameters for filtering/pagination: `GET /api/users?page=0&size=10&name=John`
- Return appropriate HTTP status codes:
  - `200 OK`: Successful GET, PUT, PATCH
  - `201 Created`: Successful POST
  - `204 No Content`: Successful DELETE
  - `400 Bad Request`: Invalid input
  - `404 Not Found`: Resource not found
  - `500 Internal Server Error`: Server error
- Use consistent response wrapper for all endpoints.

### Use Cases Pattern
- **Use Cases** orchestrate business operations without implementing complex logic.
- **Structure**: Interface + Implementation pattern (`interface` + `Impl` class).
- **Location**: `domain/usecase` (interfaces) and `domain/usecase/impl` (implementations).
- **Responsibilities**:
  - Inject services via constructor injection.
  - Call appropriate service methods to execute the operation.
  - Register operations in logs (operations, not internal details).
  - Return results to the controller.
- **Orchestration**: Use Cases coordinate service calls for complex workflows.
  - Example: `CreateUserUseCase` → `UserService.createUser()` → `UserRepository.save()`.
- **No Direct Repository Access**: Use Cases always delegate to services, never access repositories directly.
- **Logging**: Log use case start/completion for operation tracking.
- **Annotation**: Mark as `@Component` for Spring to auto-inject.
- **Example**:
```java
@Component
public class CreateUserUseCaseImpl implements CreateUserUseCase {
    private final UserService userService;
    
    public CreateUserUseCaseImpl(UserService userService) {
        this.userService = userService;
    }
    
    @Override
    public User execute(String username, Integer age) {
        log.info("Executing CreateUserUseCase...");
        User createdUser = userService.createUser(username, age);
        log.info("User created with ID: {}", createdUser.getId());
        return createdUser;
    }
}
```

## Frontend (Angular)
- Version: Angular 21.
- **Strictly use Standalone Components**. No shared modules.
- Use **Signals** for reactive state management.
- Use the **new @defer syntax** for lazy loading components.
- Services should handle all business logic; components only handle UI state.

### Dependency Management
- Use **npm** as the package manager for Angular projects.
- Keep package.json organized with clear dependency sections.
- Use only verified and stable versions from npm registry.
- Install dependencies regularly: `npm install`.

## Error Handling
- Backend: Use a `@ControllerAdvice` for global exception handling.
- Frontend: Use an `HttpInterceptor` for global error catching.