package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.service.AuthorService;
import com.example.library.service.BookService;
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
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AuthorService authorService;

    @GetMapping("/allbooks")
    public String books(ModelMap modelMap) {
        modelMap.addAttribute("books", bookService.getAllBooks());
        return "allbookstable";
    }

    @GetMapping("/insertbook")
    public String insertBook(ModelMap modelMap) {
        Book book = new Book();
        modelMap.addAttribute("book", book);
        modelMap.addAttribute("categories", categoryService.getAllCategories());
        modelMap.addAttribute("authors", authorService.getAllAuthors());
        return "bookform";
    }

    @PostMapping("/doinstertbook")
    public String doInsertMedicine(@Validated @ModelAttribute Book book, BindingResult result,
                                   ModelMap modelMap,
                                   RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "bookform";
        }
        book.setAvailableQuantity(book.getTotalQuantity());
        book.setAvailability(true);
        bookService.insertBook(book);
        redirectAttributes.addFlashAttribute("infomessage", "Book " + book.getTitle() + " " +
                "inserted successfully");

        return "redirect:/showdashboard";
    }

    @GetMapping("/bookedit/{bookIsbn}")
    public String updateBook(@PathVariable(name = "bookIsbn") final String bookIsbn,
                             ModelMap modelMap) {
        modelMap.addAttribute("book", bookService.getBookByIsbn(bookIsbn));
        modelMap.addAttribute("authors", authorService.getAllAuthors());
        modelMap.addAttribute("categories", categoryService.getAllCategories());
        return "bookedit";
    }

    @PostMapping("/doupdatebook")
    public String doUpdateBook(@Validated @ModelAttribute Book updatedBook, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            model.addAttribute("authors", authorService.getAllAuthors());
            model.addAttribute("categories", categoryService.getAllCategories());
            return "bookedit";
        }

        Book existingBook = bookService.getBookByIsbn(updatedBook.getIsbn());
        Long differenceAvailableQuantity = existingBook.getTotalQuantity() - existingBook.getAvailableQuantity();


        existingBook.setTitle(updatedBook.getTitle());
        existingBook.setReleaseDate(updatedBook.getReleaseDate());
        existingBook.setCategory(updatedBook.getCategory());
        existingBook.setAuthor(updatedBook.getAuthor());
        existingBook.setAvailableQuantity(updatedBook.getTotalQuantity() - differenceAvailableQuantity);
        existingBook.setTotalQuantity(updatedBook.getTotalQuantity());

        // Update the book in database
        bookService.updateBook(existingBook);

        return "redirect:/allbooks";
    }

    @PostMapping("/bookdelete/{bookIsbn}")
    public String deleteBook(@PathVariable(name = "bookIsbn") final String bookIsbn,
                                 ModelMap modelMap) {
        bookService.disableBookByIsbn(bookIsbn);
        return "redirect:/allbooks";
    }


}
