package com.example.laba71.controller;

import com.example.laba71.model.User;
import com.example.laba71.service.BookService;
import com.example.laba71.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class BookController {
    private final BookService bookService;
    private final UserService userService;

    @PostMapping("/books/{id}/borrow")
    public String borrowBook(@PathVariable Long id, Authentication authentication) {
        String libraryCardNumber =  authentication.getName();
        User user = userService.findByLibraryCardNumber(libraryCardNumber);
        bookService.borrowBook(id, user);
        return "redirect:/auth/profile";
    }
}
