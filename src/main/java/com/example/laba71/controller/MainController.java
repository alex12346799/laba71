package com.example.laba71.controller;

import com.example.laba71.dto.BookListItemDto;
import com.example.laba71.dto.LoanRequestDto;
import com.example.laba71.dto.PageDto;
import com.example.laba71.service.BookService;
import com.example.laba71.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final BookService bookService;
    private final CategoryService categoryService;

    @GetMapping("/")
    public String index(
            @RequestParam(required = false) Long category,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false, defaultValue = "") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "9") int size,
            Model model,
            Authentication auth
    ) {

        var pageable = switch (sort) {
            case "yearAsc" -> PageRequest.of(page, size, Sort.by("publicationYear").ascending().and(Sort.by("title").ascending()));
            case "yearDesc" -> PageRequest.of(page, size, Sort.by("publicationYear").descending().and(Sort.by("title").ascending()));
            default -> PageRequest.of(page, size, Sort.by("title").ascending());
        };

        Page<BookListItemDto> books = bookService.search(q, year, category, pageable);

        model.addAttribute("pageDto", PageDto.from(books));
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("currentCategory", category);
        model.addAttribute("q", q);
        model.addAttribute("year", year);
        model.addAttribute("sort", sort);
        model.addAttribute("authenticatedCard", auth != null ? auth.getName() : null);
        if (!model.containsAttribute("loanRequestDto")) {
            model.addAttribute("loanRequestDto", new LoanRequestDto());
        }


        return "index";
    }


}
