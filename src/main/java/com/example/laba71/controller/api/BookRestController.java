package com.example.laba71.controller.api;

import com.example.laba71.dto.BookListItemDto;
import com.example.laba71.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookRestController {
    private final BookService bookService;

    @GetMapping("/search")
    public List<BookListItemDto> liveSearch(@RequestParam("q") String q) {
        String trimmed = (q == null ? "" : q.trim());
        Integer year = null;
        if (trimmed.matches("\\d{4}")) {
            year = Integer.valueOf(trimmed);
            trimmed = "";
        }

        return bookService.search(trimmed, year, null, PageRequest.of(0, 8)).getContent();
    }
}
