# Git Commit Instructions

This document defines how to write clear, consistent commit messages.

---

## 🧾 Commit Message Structure

Each commit message should follow this format:

<type>: <short summary>

<optional detailed description>

---

## ✏️ Example

feat: add login validation

Add validation logic to improve login reliability and security.

- Validate email format
- Enforce minimum password length
- Display error messages in UI

---

## 🔤 Commit Types

Use one of the following types:

- feat     → A new feature
- fix      → A bug fix
- docs     → Documentation only changes
- style    → Code style (formatting, missing semicolons, etc.)
- refactor → Code change that neither fixes a bug nor adds a feature
- test     → Adding or updating tests
- chore    → Maintenance tasks (build, dependencies, configs)

---

## 📏 Rules

- Use lowercase for type
- Keep summary under 50–72 characters
- Use imperative mood (e.g., "add" not "added")
- Do NOT end the summary with a period
- Add a blank line between summary and description
- Focus on *why* and *what*, not implementation details

---

## ✅ Good Examples

feat: add user authentication

fix: handle null pointer in config loader

docs: update API usage guide

refactor: simplify payment processing logic

---

## ❌ Bad Examples

added login stuff

fix bug

update

---

## 🧪 Testing Section (Optional)

If relevant, include:

Testing:
- Steps performed
- Edge cases tested

---

## 🔗 Issue Reference (Optional)

You can reference issues:

feat: add password reset feature

Closes #123

---

## ⚙️ Tools Integration

This format works well with:

- Git CLI
- IntelliJ IDEA
- Commit lint tools
- CI/CD pipelines

---

## 🚀 Tips

- Make small, focused commits
- Commit often
- Review changes before committing
- Use meaningful messages for future maintainers