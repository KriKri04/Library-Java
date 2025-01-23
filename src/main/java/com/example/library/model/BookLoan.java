package com.example.library.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "book_loan")
public class BookLoan {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_username", nullable = false)
    private AppUser user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_isbn", nullable = false)
    private Book book;

    @Column(name = "borrowed", nullable = false)
    private Instant borrowed;

    @Column(name = "returned", nullable = true)
    private Instant returned;

    @Column(name = "due_date", nullable = false)
    private Instant dueDate;

    @Lob
    @Column(name = "status", nullable = false)
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user= user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book= book;
    }

    public Instant getBorrowed() {
        return borrowed;
    }

    public void setBorrowed(Instant borrowed) {
        this.borrowed = borrowed;
    }

    public Instant getReturned() {
        return returned;
    }

    public void setReturned(Instant returned) {
        this.returned = returned;
    }

    public Instant getDueDate() {
        return dueDate;
    }

    public void setDueDate(Instant dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}