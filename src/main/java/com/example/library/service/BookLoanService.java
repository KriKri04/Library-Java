package com.example.library.service;

import com.example.library.Repository.BookLoanRepository;
import com.example.library.dto.BookBorrowDto;
import com.example.library.model.Book;
import com.example.library.model.BookLoan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class BookLoanService {
    @Autowired
    private BookLoanRepository bookLoanRepository;
    @Autowired
    private BookService bookService;
    @Autowired
    private AppUserService appUserService;

    public void saveBookLoan(BookLoan bookLoan) {
        bookLoanRepository.save(bookLoan);
    }
    public List<BookLoan> getAllActiveBookLoans() {
        return bookLoanRepository.findAll()
                .stream()
                .filter(bookLoan ->
                        bookLoan.getStatus().equals("BORROWED") || bookLoan.getStatus().equals("OVERDUE")).toList();
    }

    public List<BookLoan> getAllLoans() {
        return bookLoanRepository.findAll();
    }

    public void markBookLoanByIdAsReturned(Long id) {
        BookLoan bookLoan = bookLoanRepository.findById(id).orElse(null);
        if (bookLoan == null) return;
        bookLoan.setReturned(Instant.now());
        bookLoan.setStatus("RETURNED");
        bookLoanRepository.save(bookLoan);
    }

    public BookLoan getBookLoanById(Long id) {
        return bookLoanRepository.findById(id).orElse(null);
    }
    public void loanBook(String isbn, String username) {
        BookLoan bookLoan = new BookLoan();
        bookLoan.setBook(bookService.getBookByIsbn(isbn));
        bookLoan.setUser(appUserService.findAppUserByUsername(username));
        bookLoan.setBorrowed(Instant.now());
        bookLoan.setDueDate(Instant.now().plus(30, ChronoUnit.DAYS));
        bookLoan.setStatus("BORROWED");
        saveBookLoan(bookLoan);
    }

    public BookBorrowDto getMostBorrowedBook() {
        return bookLoanRepository.findMostBorrowedBook();
    }
}
