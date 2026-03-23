# 📊 Code Coverage & Mutation Testing Guide

## Overview

Este proyecto utiliza dos herramientas para asegurar la calidad de los tests:

1. **JaCoCo** - Code Coverage (mínimo 85%)
2. **PiTest** - Mutation Testing (mínimo 80%)

---

## 🎯 Objetivos de Cobertura

| Métrica | Target | Verificación |
|---------|--------|--------------|
| **JaCoCo** | ≥ 85% | `mvn test jacoco:report` |
| **PiTest** | ≥ 80% | `mvn org.pitest:pitest-maven:mutationCoverage` |

---

## 🔍 JaCoCo - Code Coverage

### Qué es JaCoCo?
JaCoCo (Java Code Coverage) mide qué porcentaje del código fuente está cubierto por tests.

### Instalación
```xml
<!-- Ya configurado en pom.xml -->
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.10</version>
</plugin>
```

### Ejecutar Tests con JaCoCo

#### 1. Generar reporte de cobertura
```bash
mvn test jacoco:report
```

#### 2. Ver el reporte
```bash
# Windows
start target/site/jacoco/index.html

# Mac
open target/site/jacoco/index.html

# Linux
firefox target/site/jacoco/index.html
```

#### 3. Estructura del reporte
```
target/site/jacoco/
├── index.html          (Índice general)
├── overview.html       (Resumen)
├── com.carusato.restapi/
│   ├── domain/
│   ├── application/
│   ├── presentation/
│   └── infrastructure/
```

### Métricas JaCoCo

El reporte muestra:

```
┌─────────────────────────────────────┐
│ LINE COVERAGE    (Líneas cubiertas)  │
│ BRANCH COVERAGE  (Ramas condicionales) │
│ METHOD COVERAGE  (Métodos cubiertos) │
│ CLASS COVERAGE   (Clases cubiertas)  │
└─────────────────────────────────────┘
```

### Interpretar los Colores

- 🟢 **Verde** (> 85%) - Excelente cobertura
- 🟡 **Amarillo** (50-85%) - Cobertura aceptable
- 🔴 **Rojo** (< 50%) - Cobertura insuficiente

### Ejemplo: Verificar Cobertura Específica

```bash
# Solo compilar y testear (sin fallar si no cumple 85%)
mvn test

# Compilar, testear y verificar mínimo 85%
mvn test jacoco:check
```

### Configuración en pom.xml

```xml
<execution>
    <id>jacoco-check</id>
    <goals>
        <goal>check</goal>
    </goals>
    <configuration>
        <rules>
            <rule>
                <element>PACKAGE</element>
                <limits>
                    <limit>
                        <counter>LINE</counter>
                        <value>COVEREDRATIO</value>
                        <minimum>0.85</minimum>
                    </limit>
                </limits>
            </rule>
        </rules>
    </configuration>
</execution>
```

---

## 🔬 PiTest - Mutation Testing

### Qué es PiTest?

PiTest (Mutation Testing) verifica la **calidad de los tests** mediante:
1. Modificar el código fuente (mutar)
2. Ejecutar los tests contra el código mutado
3. Verificar si los tests detectan los cambios

**Si un test no detecta una mutación, significa que el test es débil.**

### Instalación
```xml
<!-- Ya configurado en pom.xml -->
<plugin>
    <groupId>org.pitest</groupId>
    <artifactId>pitest-maven</artifactId>
    <version>1.14.4</version>
</plugin>
```

### Ejecutar Mutation Testing

#### 1. Generar reporte de mutaciones
```bash
mvn org.pitest:pitest-maven:mutationCoverage
```

Este comando tarda más tiempo (5-10 minutos típicamente).

#### 2. Ver el reporte
```bash
# Windows
start target/pit-reports/index.html

# Mac
open target/pit-reports/index.html

# Linux
firefox target/pit-reports/index.html
```

#### 3. Estructura del reporte
```
target/pit-reports/
├── index.html
├── com.carusato.restapi.domain/
├── com.carusato.restapi.application/
├── com.carusato.restapi.presentation/
└── com.carusato.restapi.infrastructure/
```

### Tipos de Mutaciones

PiTest aplica mutaciones como:

```java
// Original
if (age > 0) { ... }

// Mutaciones
if (age >= 0) { ... }  // Cambia > a >=
if (age < 0) { ... }   // Invierte la condición
if (true) { ... }      // Siempre true
if (false) { ... }     // Siempre false
```

### Interpretar el Reporte

#### Estados de Mutación

| Estado | Significado | Acción |
|--------|-------------|--------|
| 🟢 **KILLED** | Test detectó la mutación | ✅ Bien |
| 🔴 **SURVIVED** | Test NO detectó la mutación | ⚠️ Mejorar test |
| 🟡 **TIMED_OUT** | Mutación causó bucle infinito | ℹ️ Ignorar |
| ⚪ **NOT_COVERED** | Código sin tests | ❌ Agregar tests |

### Ejemplo: Mejorar Tests con PiTest

#### ❌ Test Débil (SURVIVED)
```java
@Test
void shouldValidateAge() {
    // Este test solo verifica que NO lance excepción
    CreateUserRequest request = new CreateUserRequest("john", 30);
    assertThat(request).isNotNull();  // ⚠️ Débil
}
```

Mutación sobrevive: Si cambias `> 150` a `>= 150`, el test sigue pasando.

#### ✅ Test Fuerte (KILLED)
```java
@Test
void shouldThrowExceptionForAgeOver150() {
    // Este test verifica específicamente el límite
    assertThatThrownBy(() -> new CreateUserRequest("john", 151))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Age must be between 0 and 150");
}

@Test
void shouldAcceptAge150() {
    // Este test verifica el límite superior válido
    CreateUserRequest request = new CreateUserRequest("john", 150);
    assertThat(request.getAge()).isEqualTo(150);
}
```

Ahora las mutaciones son detectadas porque tests son específicos.

### Configuración en pom.xml

```xml
<configuration>
    <!-- Clases a mutar -->
    <targetClasses>
        <param>com.carusato.restapi.*</param>
    </targetClasses>
    
    <!-- Tests a ejecutar -->
    <targetTests>
        <param>com.carusato.restapi.*</param>
    </targetTests>
    
    <!-- Formato de reporte -->
    <outputFormats>
        <outputFormat>HTML</outputFormat>
    </outputFormats>
    
    <!-- Umbral de mutaciones (80%) -->
    <mutationThreshold>80</mutationThreshold>
</configuration>
```

---

## 📋 Workflow: Código → Tests → Cobertura

```
1. ESCRIBIR CÓDIGO
   └─ Com métodos y lógica

2. ESCRIBIR TESTS
   └─ Tests unitarios con Mockito + AssertJ
   └─ Cubrir casos de éxito y error
   └─ Tests específicos y claros

3. EJECUTAR JACOCO
   mvn test jacoco:report
   └─ Verificar ≥ 85% cobertura
   └─ Ver qué líneas NO están cubiertas
   └─ Agregar tests si falta cobertura

4. EJECUTAR PITEST
   mvn org.pitest:pitest-maven:mutationCoverage
   └─ Verificar ≥ 80% mutaciones detectadas
   └─ Ver qué mutaciones SURVIVED
   └─ Mejorar tests para detectar mutaciones

5. REFACTOR
   └─ Mejorar código o tests si es necesario
   └─ Repetir desde paso 3
```

---

## 🎯 Buenas Prácticas

### ✅ Tests Que Matan Mutaciones

```java
// ✅ BUENO: Verifica el comportamiento específico
@Test
void shouldThrowWhenUsernameDuplicated() {
    CreateUserRequest request1 = new CreateUserRequest("john", 30);
    
    assertThatThrownBy(() -> userService.createUser("john", 25))
            .isInstanceOf(UserAlreadyExistsException.class)
            .hasMessageContaining("john");
}

// ❌ MALO: No verifica el comportamiento
@Test
void shouldCreateUser() {
    CreateUserRequest request = new CreateUserRequest("john", 30);
    assertThat(request).isNotNull();  // Muy genérico
}
```

### ✅ Límites y Casos Especiales

```java
// ✅ BUENO: Verifica límites
@Test
void shouldAcceptAge0() {
    CreateUserRequest req = new CreateUserRequest("john", 0);
    assertThat(req.getAge()).isEqualTo(0);
}

@Test
void shouldAcceptAge150() {
    CreateUserRequest req = new CreateUserRequest("john", 150);
    assertThat(req.getAge()).isEqualTo(150);
}

@Test
void shouldRejectAge151() {
    assertThatThrownBy(() -> new CreateUserRequest("john", 151))
            .isInstanceOf(IllegalArgumentException.class);
}

// ❌ MALO: No verifica límites
@Test
void shouldValidateAge() {
    new CreateUserRequest("john", 30);  // Solo un caso
    // No verifica 0, 150, 151, -1, etc.
}
```

### ✅ Verificar Excepciones

```java
// ✅ BUENO: Verifica tipo y mensaje
assertThatThrownBy(() -> userService.deleteUser(999L))
        .isInstanceOf(UserNotFoundException.class)
        .hasMessageContaining("999");

// ❌ MALO: No verifica mensaje
assertThatThrownBy(() -> userService.deleteUser(999L))
        .isInstanceOf(UserNotFoundException.class);
```

---

## 📈 Checklist de Coverage

```
┌─ JACOCO (Code Coverage)
│  ✅ Ejecutar: mvn test jacoco:report
│  ✅ Meta: ≥ 85%
│  ✅ Revisar: target/site/jacoco/index.html
│  ✅ Verificar: Todas las clases con tests
│  ✅ No olvidar: Tests de excepciones
│
├─ PITEST (Mutation Testing)
│  ✅ Ejecutar: mvn org.pitest:pitest-maven:mutationCoverage
│  ✅ Meta: ≥ 80%
│  ✅ Revisar: target/pit-reports/index.html
│  ✅ Matar: Todas las mutaciones posibles
│  ✅ Mejorar: Tests para matar SURVIVED
│
└─ INTEGRACIÓN
   ✅ CI/CD: Incluir coverage en pipeline
   ✅ Pre-commit: Verificar antes de push
   ✅ Pull Request: Requerirlo en code review
```

---

## 🚀 Integración en CI/CD (GitHub Actions)

```yaml
name: Code Coverage
on: [push, pull_request]
jobs:
  coverage:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - uses: actions/setup-java@v2
        with:
          java-version: '21'
      
      - name: Run Tests with JaCoCo
        run: mvn test jacoco:report
      
      - name: Check JaCoCo Coverage
        run: mvn jacoco:check
      
      - name: Run Mutation Tests
        run: mvn org.pitest:pitest-maven:mutationCoverage
      
      - name: Upload Coverage Reports
        uses: codecov/codecov-action@v2
```

---

## 📊 Comandos Rápidos

```bash
# Solo tests
mvn test

# Tests + JaCoCo report
mvn test jacoco:report

# Tests + JaCoCo check (falla si < 85%)
mvn test jacoco:check

# Tests + Mutation testing
mvn org.pitest:pitest-maven:mutationCoverage

# Todo (tests, JaCoCo, PiTest)
mvn clean test jacoco:report org.pitest:pitest-maven:mutationCoverage

# Limpiar reportes
mvn clean
```

---

## 📚 Referencias

- [JaCoCo Documentation](https://www.jacoco.org/jacoco/)
- [PiTest Documentation](http://pitest.org/)
- [Code Coverage Best Practices](https://en.wikipedia.org/wiki/Code_coverage)
- [Mutation Testing](https://en.wikipedia.org/wiki/Mutation_testing)

---

**Objetivo: Código de Alta Calidad con Tests Confiables** ✨

