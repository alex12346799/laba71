package com.example.laba71.controller;

import com.example.laba71.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final BookService bookService;

//    @GetMapping
//    public String index(Model model) {
//
//        var bookList = bookService.getBookList();
//        model.addAttribute("bookList", bookList);
//        return "index";
//    }
@GetMapping
public String index(Model model) {
    var bookList = bookService.getBookList();
    if (bookList == null) {
        bookList = new ArrayList<>(); // пустой список вместо null
    }
    model.addAttribute("bookList", bookList);
    return "index";
}

}
