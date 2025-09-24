package com.example.laba71.controller;

import com.example.laba71.dto.RegistrationDto;
import com.example.laba71.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@Slf4j
@RequestMapping("/auth")
public class UserController {
    private final UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registrationDto", new RegistrationDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute RegistrationDto registrationDto,
                               BindingResult bindingResult,
                               Model model) {

        if (bindingResult.hasErrors()) {
            return "register";
        }

        String result = userService.register(registrationDto);

        if ("Пользователь успешно зарегистрирован!".equals(result)) {
            log.info("Регистрация успешна: {}", registrationDto.getPassportNumber());
            model.addAttribute("successMessage", result);
            return "login";
        } else {
            model.addAttribute("errorMessage", result);
            return "register";
        }
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String libraryCardNumber,
                            @RequestParam String password,
                            Model model) {

        Authentication auth = userService.authenticate(libraryCardNumber, password);

        if (auth != null) {
            SecurityContextHolder.getContext().setAuthentication(auth);
            return "redirect:/";
        } else {
            model.addAttribute("errorMessage", "Неверный логин или пароль");
            return "login";
        }
    }



}
