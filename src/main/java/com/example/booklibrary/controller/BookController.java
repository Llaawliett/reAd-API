package com.example.booklibrary.controller;

import com.example.booklibrary.dto.BookRequest;
import com.example.booklibrary.dto.ProgressRequest;
import com.example.booklibrary.entity.Book;
import com.example.booklibrary.entity.BookStatus;
import com.example.booklibrary.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST-контроллер для управления книгами.
 *
 * Базовый путь: /api/books
 */
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /** GET /api/books            — все книги */
    /** GET /api/books?status=... — книги с конкретным статусом */
    @GetMapping
    public List<Book> getAll(@RequestParam(required = false) BookStatus status) {
        if (status != null) {
            return bookService.findByStatus(status);
        }
        return bookService.findAll();
    }

    /** GET /api/books/{id} — одна книга по id */
    @GetMapping("/{id}")
    public Book getOne(@PathVariable Long id) {
        return bookService.findById(id);
    }

    /** POST /api/books — создать книгу */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book create(@Valid @RequestBody BookRequest request) {
        return bookService.create(request);
    }

    /** PUT /api/books/{id} — полностью обновить книгу */
    @PutMapping("/{id}")
    public Book update(@PathVariable Long id, @Valid @RequestBody BookRequest request) {
        return bookService.update(id, request);
    }

    /** DELETE /api/books/{id} — удалить книгу */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }

    /**
     * POST /api/books/{id}/progress — отметить, что почитали
     * (прибавить страницы; если дочитали — статус станет DONE).
     */
    @PostMapping("/{id}/progress")
    public Book registerProgress(@PathVariable Long id, @Valid @RequestBody ProgressRequest request) {
        return bookService.registerProgress(id, request.getPages());
    }
}
