package com.example.library.controller;

import com.example.library.dto.BookBorrowDto;
import com.example.library.model.AppUser;
import com.example.library.model.Book;
import com.example.library.security.SecurityUtil;
import com.example.library.service.AppUserService;
import com.example.library.service.BookLoanService;
import com.example.library.service.BookService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private BookLoanService bookLoanService;

    @GetMapping("/showdashboard")
    public String index(ModelMap modelMap, Model model) {
        AppUser user = appUserService.findAppUserByUsername(SecurityUtil.getCurrentUsername());
        BookBorrowDto book = bookLoanService.getMostBorrowedBook();
        model.addAttribute("book", book);
        modelMap.addAttribute("user", user);
        return "index";
    }

}
