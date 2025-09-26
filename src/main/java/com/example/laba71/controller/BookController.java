package com.example.laba71.controller;

import com.example.laba71.dto.BookDto;
import com.example.laba71.dto.LoanRequestDto;
import com.example.laba71.mapper.BookMapper;
import com.example.laba71.model.Book;
import com.example.laba71.model.Loan;
import com.example.laba71.model.User;
import com.example.laba71.service.BookService;
import com.example.laba71.service.LoanService;
import com.example.laba71.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@AllArgsConstructor
public class BookController {
    private final BookService bookService;
    private final UserService userService;
    private final BookMapper bookMapper;
    private final LoanService loanService;

//    @PostMapping("/books/{id}/borrow")
//    public String borrowBook(@PathVariable Long id, Authentication authentication) {
//        String libraryCardNumber =  authentication.getName();
//        User user = userService.findByLibraryCardNumber(libraryCardNumber);
//        bookService.borrowBook(id, user);
//        return "redirect:/auth/profile";
//    }


    @GetMapping("/books/{id}/borrow")
    public String showBorrowForm(@PathVariable Long id, Model model, Authentication authentication) {
        Book book = bookService.getBookById(id);
        List<Loan> loans = loanService.getLoansByBookId(id);

        BookDto bookDto = BookMapper.toBookDto(book, loans);
        model.addAttribute("bookDto", bookDto);
        model.addAttribute("loanRequestDto", new LoanRequestDto());

        model.addAttribute("libraryCardNumber", authentication.getName());
        return "borrow-form";
    }

    @PostMapping("/books/{id}/borrow")
    public String submitBorrowForm(@PathVariable Long id,
                                   @Valid @ModelAttribute LoanRequestDto loanRequestDto,
                                   BindingResult bindingResult,
                                   Model model,
                                   Authentication authentication) {
        log.error("we are in controller!!!!");
        log.error("authentication: " + authentication.getName());
        Book book = bookService.getBookById(id);
        List<Loan> loans = loanService.getLoansByBookId(id);

        if (bindingResult.hasErrors()) {
            model.addAttribute("bookDto", BookMapper.toBookDto(book, loans));
            return "borrow-form";
        }

        User user = userService.findByLibraryCardNumber(authentication.getName());
        if (user == null) {
            model.addAttribute("bookDto", BookMapper.toBookDto(book, loans));
            model.addAttribute("error", "Неверный номер читательского билета");
            return "borrow-form";
        }

        try {
            bookService.borrowBook(id, user, loanRequestDto.getDueDate());
        } catch (IllegalStateException e) {
            model.addAttribute("bookDto", BookMapper.toBookDto(book, loans));
            model.addAttribute("error", e.getMessage());
            return "borrow-form";
        }

        return "redirect:/auth/profile";
    }







}
