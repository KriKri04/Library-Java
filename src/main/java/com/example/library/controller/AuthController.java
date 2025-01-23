package com.example.library.controller;

import com.example.library.dto.RegistrationDto;
import com.example.library.model.AppUser;
import com.example.library.service.AppUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final AppUserService appUserService;

    public AuthController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }
    @PostMapping("/register")
    public String registerUser(@Validated @ModelAttribute("user") RegistrationDto user,
                           BindingResult bindingResult, Model model) {
        AppUser existingUser = appUserService.findAppUserByUsername(user.getUsername());

        if (existingUser != null) {
            bindingResult.rejectValue("username", "error.user", "There is already a user registered with the username provided");
        }

        if (bindingResult.hasErrors()) {
            return "register";
        }
            appUserService.saveAppUser(user);
            return "redirect:/login";
    }
    @GetMapping("/register")
    public String registerUser(Model model) {
        model.addAttribute("user", new RegistrationDto());
        return "register";
    }
    @GetMapping("/login")
    public String loginUser() {
        return "login";
    }
}
