package com.example.library.controller;

import com.example.library.dto.BookLoanDto;
import com.example.library.model.Book;
import com.example.library.model.BookLoan;
import com.example.library.service.AppUserService;
import com.example.library.service.BookLoanService;
import com.example.library.service.BookService;
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
public class LoanController {
    @Autowired
    private BookLoanService bookLoanService;
    @Autowired
    private BookService bookService;
    @Autowired
    private AppUserService appUserService;

    @GetMapping("/allactiveloans")
    public String allActiveLoans(ModelMap modelMap) {
        modelMap.addAttribute("bookLoans", bookLoanService.getAllActiveBookLoans());
        return "allactiveloanstable";
    }

    @GetMapping("/allloans")
    public String allLoans(ModelMap modelMap) {
        modelMap.addAttribute("bookLoans", bookLoanService.getAllLoans());
        return "allloanstable";
    }

    @GetMapping("/insertloan")
    public String insertLoan(ModelMap modelMap) {
        BookLoanDto bookLoanDto = new BookLoanDto();
        modelMap.addAttribute("bookLoanDto", bookLoanDto);
        return "loanform";

    }

    @PostMapping("/doinsertloan")
    public String doInsertLoan(@ModelAttribute("bookLoanDto") BookLoanDto bookLoanDto,
                               BindingResult result) {
       if (result.hasErrors()) {
           return "loanform";
       }
       bookLoanService.loanBook(bookLoanDto.getIsbn(), bookLoanDto.getUsername());
       return "redirect:/showdashboard";
    }

    @PostMapping("/returnloan/{loanId}")
    public String returnLoan(@PathVariable(name = "loanId") final Long loanId) {
        bookLoanService.markBookLoanByIdAsReturned(loanId);
        return "redirect:/showdashboard";
    }
}
