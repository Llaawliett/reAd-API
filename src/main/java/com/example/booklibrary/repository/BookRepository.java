package com.example.booklibrary.repository;

import com.example.booklibrary.entity.Book;
import com.example.booklibrary.entity.BookStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByStatus(BookStatus status);

    List<Book> findByTitleContainingIgnoreCase(String title);
}
