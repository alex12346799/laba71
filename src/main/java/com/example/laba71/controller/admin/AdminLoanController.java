package com.example.laba71.controller.admin;

import com.example.laba71.dto.LoanReturnDto;
import com.example.laba71.dto.LoanSearchFilterDto;
import com.example.laba71.service.AdminLoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/admin/loans")
@RequiredArgsConstructor
public class AdminLoanController {
    private final AdminLoanService loanService;

    @GetMapping
    public String list(@ModelAttribute LoanSearchFilterDto filter, Model model) {
        model.addAttribute("filter", filter);
        model.addAttribute("pageDto", loanService.search(filter));
        return "admin/loans/list";
    }

    @PostMapping("/return")
    public String markReturned(@ModelAttribute @Valid LoanReturnDto dto) {
        loanService.markReturned(dto.getLoanId(), dto.getReturnedAt());
        return "redirect:/admin/loans";
    }
}
