package com.example.booklibrary.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO для создания/обновления книги. Отделён от сущности,
 * чтобы наружу не "протекали" детали хранения (id, статус).
 */
public class BookRequest {

    @NotBlank(message = "title обязателен")
    private String title;

    private String author;

    @Min(value = 0, message = "totalPages не может быть отрицательным")
    private int totalPages;

    private String link;

    public BookRequest() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
