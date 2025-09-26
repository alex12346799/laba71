package com.example.laba71.controller.admin;

import com.example.laba71.dto.ImageDto;
import com.example.laba71.model.Book;
import com.example.laba71.repository.BookRepository;
import com.example.laba71.repository.CategoryRepository;
import com.example.laba71.service.AdminBookService;
import com.example.laba71.service.CategoryService;
import com.example.laba71.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin/books")
@RequiredArgsConstructor
public class AdminBookController {
    private final BookRepository bookRepo;
    private final CategoryRepository categoryRepo;
    private final AdminBookService adminBookService;
    private final CategoryService categoryService;
    private final ImageService imageService;

    @GetMapping
    public String list(@RequestParam(defaultValue = "") String q,
                       @RequestParam(required = false) Integer year,
                       @RequestParam(required = false) Long category,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "12") int size,
                       Model model) {

        var pageDto = adminBookService.findBooks(q, year, category, page, size);
        model.addAttribute("pageDto", pageDto);
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("q", q);
        model.addAttribute("year", year);
        model.addAttribute("category", category);
        return "admin/books/list";
    }

    @PostMapping
    public String create(@RequestParam String title,
                         @RequestParam String author,
                         @RequestParam Long categoryId,
                         @RequestParam(required=false) Integer publicationYear) {
        Book b = new Book();
        b.setTitle(title.trim());
        b.setAuthor(author.trim());
        b.setPublicationYear(publicationYear);
        b.setCategory(categoryRepo.getReferenceById(categoryId));
        b.setTotalCopies(1);
        b.setAvailableCopies(1);
        bookRepo.save(b);
        return "redirect:/admin/books";
    }

    @PostMapping("/{id}/image")
    public String upload(@PathVariable Long id, @RequestParam("image") MultipartFile image) {
        imageService.uploadImage(
                ImageDto
                        .builder()
                        .bookId(id)
                        .image(image)
                        .build()
        );
        return "redirect:/admin/books";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        bookRepo.deleteById(id);
        return "redirect:/admin/books";
    }
}