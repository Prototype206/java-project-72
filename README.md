### Hexlet tests and linter status:
[![Actions Status](https://github.com/Prototype206/java-project-72/actions/workflows/hexlet-check.yml/badge.svg)](https://github.com/Prototype206/java-project-72/actions)
[![Java CI](https://github.com/Prototype206/java-project-72/actions/workflows/ci.yml/badge.svg)](https://github.com/Prototype206/java-project-72/actions)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=Prototype206_java-project-72&metric=coverage)](https://sonarcloud.io/summary/new_code?id=Prototype206_java-project-72)

# Page Analyzer (Анализатор страниц)

**Анализатор страниц** — это веб-приложение на базе Java и Javalin для мониторинга и SEO-анализа сайтов. Оно позволяет регистрировать веб-ресурсы, сохранять их в базу данных и выполнять регулярные проверки доступности с парсингом ключевых метаданных (теги `title`, `h1` и `description`).

## Demo
[Приложение на Render](https://java-project-72-8coq.onrender.com)

## Запуск приложения

```bash
# Сборка проекта
make setup

# Запуск в режиме разработки
./gradlew run