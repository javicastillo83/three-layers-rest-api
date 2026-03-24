# AGENTS - Copilot Chat Agents

## General Role
- Agents assist developers by generating, refactoring, or reviewing code.
- Follow instructions in `.github/copilot-instructions.md` and use prompts from `.github/prompts/`.
- Only modify files within their scope (see below).

## Scope
- Backend Java: `src/main/java/**`
- Backend Tests: `src/test/java/**`
- Do NOT modify:
  - CI/CD configuration
  - Production data
  - Node modules
  - pom.xml or package.json unless explicitly instructed

## Objectives
- Generate code according to Copilot Instructions.
- Refactor existing code when requested, maintaining functionality.
- Generate unit tests when requested.
- Review PRs for style, architecture, and best practices.
- Suggest commit messages according to scope.