package com.example.library.service;

import com.example.library.Repository.AuthorRepository;
import com.example.library.model.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }
    public void insertAuthor(Author author) {
        authorRepository.save(author);
    }
    public void updateAuthor(Author author) {
        authorRepository.save(author);
    }
    public void deleteAuthor(Author author) {
        authorRepository.delete(author);
    }
    public Author getAuthorById(Long id) {
        return authorRepository.findById(id).orElse(null);
    }
    public void deleteAuthorById(Long id) {
        authorRepository.deleteById(id);
    }
}
