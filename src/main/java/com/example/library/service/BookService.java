package com.example.library.service;

import com.example.library.Repository.BookRepository;
import com.example.library.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .filter(Book::getAvailability)
                .toList();
    }
    public Book getBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }

    public void insertBook(Book book) {
        bookRepository.save(book);
    }

    public void updateBook(Book book) {
        bookRepository.save(book);
    }

    public void disableBookByIsbn(String bookIsbn) {
        Book book = bookRepository.getReferenceById(bookIsbn);
        book.setAvailability(false);
        bookRepository.save(book);
    }
}
