package com.example.library.dto;

public class BookBorrowDto {
    private String isbn;
    private String title;
    private Long borrowCount;

    public BookBorrowDto(String isbn, String title, Long borrowCount) {
        this.isbn = isbn;
        this.title = title;
        this.borrowCount = borrowCount;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getBorrowCount() {
        return borrowCount;
    }

    public void setBorrowCount(Long borrowCount) {
        this.borrowCount = borrowCount;
    }
}