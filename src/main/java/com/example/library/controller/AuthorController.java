package com.example.library.controller;

import com.example.library.model.Author;
import com.example.library.model.Category;
import com.example.library.service.AuthorService;
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
public class AuthorController {
    @Autowired
    private AuthorService authorService;


    @GetMapping("/allauthors")
    public String getAllAuthors(ModelMap modelMap) {
        modelMap.addAttribute("authors", authorService.getAllAuthors());
        return "allauthorstable";
    }

    @GetMapping("/insertauthor")
    public String insertAuthor(ModelMap modelMap) {
        Author author = new Author();
        modelMap.addAttribute("author", author);
        return "authorform";
    }

    @PostMapping("/doinsertauthor")
    public String doInsertAuthor(@Validated @ModelAttribute Author author, BindingResult result,
                                 ModelMap modelMap,
                                 RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "authorform";
        }
        authorService.insertAuthor(author);
        redirectAttributes.addFlashAttribute("infomessage", "Author " + author.getFirstName() + " " + author.getLastName() + " inserted successfully");
        return "redirect:/allauthors";
    }

    @GetMapping("/authoredit/{authorId}")
    public String updateAuthor(@PathVariable(name = "authorId") final Long authorId,
                               ModelMap modelMap) {
        modelMap.addAttribute("author", authorService.getAuthorById(authorId));
        return "authoredit";
    }

    @PostMapping("/doupdateauthor")
    public String doUpdateAuthor(@Validated @ModelAttribute Author author, BindingResult result) {
        if (result.hasErrors()) {
            return "authoredit";
        }
        authorService.updateAuthor(author);
        return "redirect:/allauthors";
    }

    @PostMapping("/authordelete/{authorId}")
    public String deleteAuthor(@PathVariable(name = "authorId") final Long authorId,
                               ModelMap modelMap, RedirectAttributes redirectAttributes) {
        try {
            authorService.deleteAuthorById(authorId);
        } catch (Exception e) {
            if (e.getMessage().contains("Cannot delete or update a parent row: a foreign key constraint fails")) {
                redirectAttributes.addFlashAttribute("infomessage", "Error: You cannot delete this author because they are still referenced by a book");
            } else {
                redirectAttributes.addFlashAttribute("infomessage", "Error: " + e.getMessage());
            }
            return "redirect:/allauthors";
        }
        return "redirect:/allauthors";
    }
}