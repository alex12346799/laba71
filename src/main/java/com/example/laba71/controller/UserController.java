package com.example.laba71.controller;


import com.example.laba71.dto.ProfileDto;
import com.example.laba71.dto.user.RegistrationDto;
import com.example.laba71.model.Loan;
import com.example.laba71.model.User;
import com.example.laba71.service.LoanService;
import com.example.laba71.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
@RequestMapping("/auth")
public class UserController {
    private final UserService userService;
    private final LoanService loanService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registrationDto", new com.example.laba71.dto.user.RegistrationDto());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute com.example.laba71.dto.user.RegistrationDto registrationDto,
                               BindingResult bindingResult,
                               Model model) {

        if (bindingResult.hasErrors()) {
            return "auth/register";
        }

        String result = userService.register(registrationDto);

        if ("Пользователь успешно зарегистрирован!".equals(result)) {
            log.info("Регистрация успешна: {}", registrationDto.getPassportNumber());
            model.addAttribute("successMessage", result);
            return "auth/login";
        } else {
            model.addAttribute("errorMessage", result);
            return "auth/register";
        }
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("error", "Неверный логин или пароль");
        return "auth/login";
    }

//    @PostMapping("/login")
//    public String loginUser(@RequestParam String libraryCardNumber,
//                            @RequestParam String password,
//                            Model model) {
//
//        Authentication auth = userService.authenticate(libraryCardNumber, password);
//
//        if (auth != null) {
//            SecurityContextHolder.getContext().setAuthentication(auth);
//            return "redirect:/";
//        } else {
//            model.addAttribute("errorMessage", "Неверный логин или пароль");
//            return "auth/login";
//        }
//    }

    @GetMapping("/profile")
    public String profile(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Model model,
            Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/auth/login";
        }

        ProfileDto profileDto = userService.getProfile(authentication.getName(), startDate, endDate);
        model.addAttribute("profileDto", profileDto);

        return "profile/profile";
    }

}
