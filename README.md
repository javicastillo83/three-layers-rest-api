# Resumen de `.github/` y ficheros generales de IA

Este documento resume la **estructura de ficheros generales** para Copilot y agentes, mostrando qué va en la raíz y qué dentro de `.github/`.

---

## 🟦 Copilot Instructions (dentro de `.github/`)

| Carpeta / Archivo | Propósito general | Contenido típico / función |
|------------------|-----------------|---------------------------|
| **.github/copilot-instructions.md** | Reglas globales de codificación para Copilot | Estilo de código, naming conventions, Clean Code, SOLID, DRY, layer rules, DTOs, logging, null safety. Aplica a todo el código que la IA debe generar o autocompletar. |
| **.github/instructions/** | Documentación de buenas prácticas y guía detallada | Arquitectura, estilo, testing, calidad y dependencias. Sirve como referencia para humanos y Copilot para contextualizar sugerencias. |
| **.github/prompts/** | Prompts específicos para tareas de Copilot | Refactor, tests, PR reviews, commits, etc. Cada prompt puede tener `applyTo` para definir el scope de aplicación (ej. `**/*.java` o `**/*Test.java`). |

---

## 🟩 Agents (Copilot Chat, Claude u otros) — ficheros en la raíz

| Carpeta / Archivo | Propósito general | Contenido típico / función |
|------------------|-----------------|---------------------------|
| **AGENTS.md** | Roles y comportamiento de agentes Copilot Chat | Define scope, objetivos, reglas de actuación, límites de modificación y contexto del proyecto. Permite que los agentes sepan qué archivos pueden tocar y cómo actuar. |
| **CLAUDE.md** | Roles y comportamiento de agentes Claude AI | Igual que AGENTS.md pero adaptado para Claude: define rol, scope, contexto y reglas de codificación y testing. |
| **.github/instructions/** | Contexto para agentes | Mismo contenido que para Copilot, los agentes pueden usar estas instrucciones como referencia para decisiones de arquitectura, estilo y testing. |
| **.github/prompts/** | Prompts reutilizables para tareas específicas de agentes | Instrucciones “on-demand” que los agentes pueden usar para refactor, generar tests, revisar PRs, crear commits, etc. |

---

### 🔹 Notas generales

- Los archivos **AGENTS.md** y **CLAUDE.md** van en la raíz del repo para que los agentes los encuentren fácilmente.
- Copilot usa principalmente `.github/copilot-instructions.md`, `instructions/` y `prompts/`.
- Carpetas **instructions/** y **prompts/** se comparten entre Copilot y agentes como referencia y fuente de prompts reutilizables.