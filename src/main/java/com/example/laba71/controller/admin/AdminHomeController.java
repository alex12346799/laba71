package com.example.laba71.controller.admin;

import com.example.laba71.dto.LoanSearchFilterDto;
import com.example.laba71.service.AdminLoanService;
import com.example.laba71.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminHomeController {
    private final AdminLoanService loanService;
    @GetMapping
    public String index(@ModelAttribute LoanSearchFilterDto filter, Model model)
    {
        model.addAttribute("pageDto", loanService.search(filter));
        return "admin/index"; }
}
