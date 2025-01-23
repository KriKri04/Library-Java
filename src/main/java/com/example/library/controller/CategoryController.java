package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.model.Category;
import com.example.library.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/allcategories")
    public String getAllCategories(ModelMap modelMap) {
        modelMap.addAttribute("categories", categoryService.getAllCategories());
        return "allcategoriestable";
    }

    @GetMapping("/insertcategory")
    public String insertCategory(ModelMap modelMap) {
        Category category = new Category();
        modelMap.addAttribute("category", category);
        return "categoryform";
    }
    @PostMapping("/doinstertcategory")
    public String doInsertCategory(@Validated @ModelAttribute Category category, BindingResult result,
                                   ModelMap modelMap,
                                   RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "categoryform";
        }
        categoryService.insertCategory(category);
        redirectAttributes.addFlashAttribute("infomessage", "Category " + category.getCategoryName() + " " +
                "inserted successfully");

        return "redirect:/allcategories";
    }

    @GetMapping("/categoryedit/{categoryId}")
    public String updateCategory(@PathVariable(name = "categoryId") final Long categoryId,
                             ModelMap modelMap) {
        modelMap.addAttribute("category", categoryService.getCategoryById(categoryId));
        return "categoryedit";
    }

    @PostMapping("/doupdatecategory")
    public String doUpdateCategory(@Validated @ModelAttribute Category category, BindingResult result) {
        if (result.hasErrors()) {
            return "categoryedit";
        }
        categoryService.updateCategory(category);
        return "redirect:/allcategories";
    }

    @PostMapping("/categorydelete/{categoryId}")
    public String deleteCategory(@PathVariable(name = "categoryId") final Long categoryId,
                                 ModelMap modelMap, RedirectAttributes redirectAttributes) {
        try {
            categoryService.deleteCategoryById(categoryId);
        } catch (Exception e) {
            if (e.getMessage().contains("Cannot delete or update a parent row: a foreign key constraint fails")) {
                redirectAttributes.addFlashAttribute("infomessage", "Error: You cannot delete this category because it is still referenced by a book");
            } else {
                redirectAttributes.addFlashAttribute("infomessage", "Error: " + e.getMessage());
            }
            return "redirect:/allcategories";

        }
        return "redirect:/allcategories";
    }
}
