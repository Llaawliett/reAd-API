package com.example.booklibrary.service;

import com.example.booklibrary.dto.BookRequest;
import com.example.booklibrary.entity.Book;
import com.example.booklibrary.entity.BookStatus;
import com.example.booklibrary.exception.BookNotFoundException;
import com.example.booklibrary.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Бизнес-логика работы с книгами: отделена от контроллера
 * (HTTP-слой) и репозитория (слой хранения).
 */
@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public List<Book> findByStatus(BookStatus status) {
        return bookRepository.findByStatus(status);
    }

    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    public Book create(BookRequest request) {
        Book book = new Book(request.getTitle(), request.getAuthor(),
                request.getTotalPages(), request.getLink());
        return bookRepository.save(book);
    }

    public Book update(Long id, BookRequest request) {
        Book book = findById(id);
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setTotalPages(request.getTotalPages());
        book.setLink(request.getLink());
        return bookRepository.save(book);
    }

    public void delete(Long id) {
        Book book = findById(id);
        bookRepository.delete(book);
    }

    /**
     * Отметить прогресс чтения: прибавить страницы и, если книга
     * дочитана до конца, перевести статус в DONE.
     */
    public Book registerProgress(Long id, int pagesDelta) {
        Book book = findById(id);

        if (book.getStatus() == BookStatus.PLANNED) {
            book.setStatus(BookStatus.READING);
        }

        int newPage = book.getCurrentPage() + pagesDelta;
        if (book.getTotalPages() > 0) {
            newPage = Math.min(newPage, book.getTotalPages());
        }
        book.setCurrentPage(Math.max(0, newPage));

        if (book.getTotalPages() > 0 && book.getCurrentPage() >= book.getTotalPages()) {
            book.setStatus(BookStatus.DONE);
        }

        return bookRepository.save(book);
    }
}
