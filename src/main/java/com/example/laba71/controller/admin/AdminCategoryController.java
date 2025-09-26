package com.example.laba71.controller.admin;

import com.example.laba71.model.Category;
import com.example.laba71.repository.CategoryRepository;
import com.example.laba71.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {
    private final CategoryRepository repo;
    private final CategoryService categoryService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("items", categoryService.getAll());
        return "admin/categories/list";
    }

    @PostMapping
    public String create(@RequestParam String name) {
        Category c = new Category(); c.setName(name.trim());
        repo.save(c);
        return "redirect:/admin/categories";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/admin/categories";
    }
}