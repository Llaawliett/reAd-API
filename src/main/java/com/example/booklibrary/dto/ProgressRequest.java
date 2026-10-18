package com.example.booklibrary.dto;

import jakarta.validation.constraints.Min;

/**
 * DTO для отметки "почитал сегодня" — на сколько страниц продвинулись.
 */
public class ProgressRequest {

    @Min(value = 0, message = "pages не может быть отрицательным")
    private int pages;

    public ProgressRequest() {
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
