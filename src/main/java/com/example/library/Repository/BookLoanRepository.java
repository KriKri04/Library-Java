package com.example.library.Repository;

import com.example.library.dto.BookBorrowDto;
import com.example.library.model.BookLoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Instant;
import java.util.List;

public interface BookLoanRepository extends JpaRepository<BookLoan, Long> {
    List<BookLoan> findBookLoansByStatusAndDueDateBefore(String status, Instant dueDate);

    @Query("SELECT new com.example.library.dto.BookBorrowDto(" +
            "b.isbn, b.title, COUNT(bl) as borrowCount) " +
            "FROM Book b " +
            "JOIN BookLoan bl ON b.isbn = bl.book.isbn " +
            "GROUP BY b.isbn, b.title " +
            "ORDER BY borrowCount DESC " +
            "LIMIT 1")
    BookBorrowDto findMostBorrowedBook();
}
