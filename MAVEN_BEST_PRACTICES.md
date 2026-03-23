# 📋 MAVEN DEPENDENCY MANAGEMENT - MEJORES PRÁCTICAS

## ✅ Actualización Realizada

He actualizado el proyecto para seguir las mejores prácticas de Maven con Spring Boot:

### 🎯 Cambios Implementados

#### 1. **Usar Parent de Spring Boot**
```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>4.0.3</version>
</parent>
```

**Beneficios:**
- ✅ Hereda versiones de dependencias comunes (Spring, JUnit, Mockito, AssertJ, etc.)
- ✅ No necesitas especificar versiones para dependencias manejadas por Spring Boot
- ✅ Menos código boilerplate en pom.xml
- ✅ Versiones consistentes y testeadas

#### 2. **Definir Versiones como Variables**
```xml
<properties>
    <java.version>21</java.version>
    <!-- Project-specific dependencies -->
    <mapstruct.version>1.5.5.Final</mapstruct.version>
    <springdoc-openapi.version>2.6.0</springdoc-openapi.version>
    <jacoco-maven-plugin.version>0.8.10</jacoco-maven-plugin.version>
    <pitest-maven.version>1.14.4</pitest-maven.version>
</properties>
```

**Beneficios:**
- ✅ Fácil actualizar versiones (cambiar en un lugar)
- ✅ Reutilizar versiones en múltiples dependencias
- ✅ Código limpio y mantenible
- ✅ Variables centralizadas

#### 3. **Usar Variables en Dependencias**
```xml
<dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct</artifactId>
    <version>${mapstruct.version}</version>
</dependency>

<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>${springdoc-openapi.version}</version>
</dependency>
```

---

## 📊 Cambios Específicos en pom.xml

### ✅ Properties Agregadas
```xml
<mapstruct.version>1.5.5.Final</mapstruct.version>
<springdoc-openapi.version>2.6.0</springdoc-openapi.version>
<jacoco-maven-plugin.version>0.8.10</jacoco-maven-plugin.version>
<pitest-maven.version>1.14.4</pitest-maven.version>
```

### ✅ Dependencias Actualizadas

**MapStruct**
```xml
<!-- ANTES -->
<version>1.5.5.Final</version>

<!-- AHORA -->
<version>${mapstruct.version}</version>
```

**SpringDoc OpenAPI**
```xml
<!-- ANTES -->
<version>3.0.2</version>

<!-- AHORA -->
<version>${springdoc-openapi.version}</version>
```

### ✅ Plugins Actualizados

**JaCoCo**
```xml
<!-- ANTES -->
<version>0.8.10</version>

<!-- AHORA -->
<version>${jacoco-maven-plugin.version}</version>
```

**PiTest**
```xml
<!-- ANTES -->
<version>1.14.4</version>

<!-- AHORA -->
<version>${pitest-maven.version}</version>
```

---

## 📝 Estándar Agregado a copilot-instructions.md

```markdown
### Dependency Management
- Inherit dependency versions from Spring Boot parent
- Define project-specific dependency versions as Maven properties
- Place version variables in <properties> section
- Use naming convention: groupId-artifactId.version
- Reference using ${property.name} syntax
```

**Sección:** Backend → Dependency Management (actualizada)

---

## 🎓 Ventajas de Esta Práctica

### ✅ Mantenibilidad
- Cambiar versión es más fácil (un solo lugar)
- Clara separación entre dependencias del framework vs proyecto

### ✅ Escalabilidad
- Fácil agregar nuevas dependencias
- Variables reutilizables

### ✅ Consistencia
- Todas las dependencias en properties
- Patrón naming consistente

### ✅ Legibilidad
- pom.xml más limpio
- Fácil ver qué versiones se usan

---

## 📂 Estructura Recomendada del pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project>
    <modelVersion>4.0.0</modelVersion>
    
    <!-- Parent Spring Boot -->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>4.0.3</version>
    </parent>
    
    <!-- Project Info -->
    <groupId>com.carusato</groupId>
    <artifactId>three-layers-rest-api</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    
    <!-- Properties - AQUÍ VAN LAS VERSIONES -->
    <properties>
        <java.version>21</java.version>
        <!-- Project-specific -->
        <mapstruct.version>1.5.5.Final</mapstruct.version>
        <springdoc-openapi.version>2.6.0</springdoc-openapi.version>
    </properties>
    
    <!-- Dependencies - USA LAS VARIABLES -->
    <dependencies>
        <!-- Spring Boot managed -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
            <!-- NO ESPECIFICAR VERSIÓN -->
        </dependency>
        
        <!-- Project-specific -->
        <dependency>
            <groupId>org.mapstruct</groupId>
            <artifactId>mapstruct</artifactId>
            <version>${mapstruct.version}</version>
        </dependency>
    </dependencies>
    
    <!-- Build -->
    <build>
        <plugins>
            <!-- Plugins con versiones en properties -->
        </plugins>
    </build>
</project>
```

---

## ✨ Ejemplo Completo

### Dependencia Spring Boot (sin versión)
```xml
<!-- Spring Boot parent manage la versión -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
    <!-- NO VERSIÓN - heredada del parent -->
</dependency>
```

### Dependencia Propia (con variable)
```xml
<!-- Versión definida como property -->
<dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct</artifactId>
    <version>${mapstruct.version}</version>
</dependency>
```

---

## 🎯 Norma Actualizada en copilot-instructions.md

Línea: "Dependency Management - Backend"

**Actualización:** Especifica claramente:
- ✅ Usar Spring Boot parent
- ✅ Heredar versiones del parent
- ✅ Solo definir versiones de dependencias propias
- ✅ Usar variables para versiones
- ✅ Naming convention: `groupId-artifactId.version`
- ✅ Referencia: `${property.name}`

---

## 📊 Resumen

| Aspecto | Antes | Ahora |
|---------|-------|-------|
| Versiones en pom | Dispersas | En properties |
| Cambiar versión | Buscar en todo el archivo | Un solo lugar |
| Dependencias Spring | Con versión explícita | Heredadas del parent |
| Claridad | Confuso | Claro |
| Mantenibilidad | Baja | Alta |

---

**Proyecto actualizado y conforme con mejores prácticas Maven** ✅

