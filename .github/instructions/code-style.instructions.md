---
applyTo: '**/*.java'
description: 'Backend Java code style and conventions'
---
# Code Style

## General
- Meaningful names
- Avoid duplication
- Keep methods small

## Documentation
- JavaDoc for public methods
- Explain WHY not WHAT
- TODO/FIXME with context

## Logging
- SLF4J + Logback
- Use correct log levels
- Never log sensitive data

## Collections
- Typed collections only
- Prefer immutable
- Avoid null collections

## Streams
- Use map/filter/reduce
- Keep chains readable
- Avoid side effects

## Constants
- Dedicated constants classes
- UPPER_SNAKE_CASE
- No magic numbers