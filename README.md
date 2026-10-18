# 📖 Book Library API

![Java](https://img.shields.io/badge/java-21-orange)
![Spring Boot](https://img.shields.io/badge/spring--boot-3.3.4-brightgreen)
![Build](https://img.shields.io/badge/build-maven-blue)
![License](https://img.shields.io/badge/license-MIT-green)

REST API для учёта книг: добавление, статус чтения, прогресс по страницам.
Pet-проект для демонстрации слоёной архитектуры Spring Boot (Controller →
Service → Repository) и REST-дизайна.

## Features

- CRUD для книг (`title`, `author`, `totalPages`, `link`, статус)
- Отметка прогресса чтения (`POST /{id}/progress`) с авто-переходом в статус `DONE`
- Фильтрация по статусу (`GET /api/books?status=READING`)
- Валидация входных данных (`jakarta.validation`)
- Единая обработка ошибок (404, 400 — аккуратный JSON вместо стектрейса)
- H2 in-memory база "из коробки" — не нужно ничего ставить, чтобы запустить
- Интеграционные тесты на `MockMvc`

## Быстрый старт

Нужны: **Java 21+** и **Maven** (или `./mvnw`, если добавишь wrapper).

```bash
cd book-library-api
mvn spring-boot:run
```

Приложение поднимется на `http://localhost:8080`.
Консоль H2 (посмотреть данные глазами): `http://localhost:8080/h2-console`
(JDBC URL: `jdbc:h2:mem:booklibrary`, логин `sa`, пароль пустой).

### Собрать jar

```bash
mvn clean package
java -jar target/book-library-api-1.0.0.jar
```

## Эндпоинты

| Метод  | Путь                      | Описание                              |
| ------ | ------------------------- | -------------------------------------- |
| GET    | `/api/books`               | Список всех книг                       |
| GET    | `/api/books?status=READING`| Книги с конкретным статусом             |
| GET    | `/api/books/{id}`          | Одна книга по id                       |
| POST   | `/api/books`                | Создать книгу                          |
| PUT    | `/api/books/{id}`          | Обновить книгу                         |
| DELETE | `/api/books/{id}`          | Удалить книгу                          |
| POST   | `/api/books/{id}/progress` | Отметить прочитанные страницы          |

Статусы: `PLANNED`, `READING`, `DONE`.

## Примеры запросов

**Создать книгу:**
```bash
curl -X POST http://localhost:8080/api/books \
  -H "Content-Type: application/json" \
  -d '{"title":"Clean Code","author":"Robert C. Martin","totalPages":464,"link":"https://example.com/clean-code"}'
```

**Отметить прогресс (прочитал 50 страниц):**
```bash
curl -X POST http://localhost:8080/api/books/1/progress \
  -H "Content-Type: application/json" \
  -d '{"pages":50}'
```

**Получить книги в процессе чтения:**
```bash
curl http://localhost:8080/api/books?status=READING
```

**Удалить книгу:**
```bash
curl -X DELETE http://localhost:8080/api/books/1
```

## Структура проекта

```
book-library-api/
├── pom.xml
└── src
    ├── main
    │   ├── java/com/example/booklibrary
    │   │   ├── BookLibraryApiApplication.java
    │   │   ├── entity/        # Book, BookStatus
    │   │   ├── repository/    # BookRepository (Spring Data JPA)
    │   │   ├── service/       # BookService — бизнес-логика
    │   │   ├── controller/    # BookController — HTTP-слой
    │   │   ├── dto/           # BookRequest, ProgressRequest
    │   │   └── exception/     # BookNotFoundException, ApiExceptionHandler
    │   └── resources/application.properties
    └── test/java/.../BookControllerIT.java
```

## Переход на PostgreSQL

По умолчанию используется H2 in-memory (данные пропадают при перезапуске).
Чтобы подключить постоянную БД — раскомментируй блок PostgreSQL в
`application.properties` и добавь зависимость в `pom.xml`:

```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

## Roadmap

- [ ] Пагинация и сортировка для `GET /api/books`
- [ ] Swagger / OpenAPI-документация
- [ ] Аутентификация (Spring Security + JWT)
- [ ] Docker-образ

## License

MIT

---
