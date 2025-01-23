package com.example.library.service;

import com.example.library.Repository.CategoryRepository;
import com.example.library.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
    public void insertCategory(Category category) {
        categoryRepository.save(category);
    }
    public void updateCategory(Category category) {
        categoryRepository.save(category);
    }
    public void deleteCategoryById(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId).orElse(null);
    }
}
