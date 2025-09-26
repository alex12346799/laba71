package com.example.laba71.controller.admin;

import com.example.laba71.dto.LoanReturnDto;
import com.example.laba71.dto.LoanSearchFilterDto;
import com.example.laba71.model.User;
import com.example.laba71.service.AdminLoanService;
import com.example.laba71.service.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@Controller
@RequestMapping("/admin/loans")
@RequiredArgsConstructor
public class AdminLoanController {
    private final AdminLoanService loanService;
    private final LoanService loanService1;

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


    @PostMapping("/{bookId}/borrow")
    public String borrowBook(
            @PathVariable Long bookId,
            @RequestParam("dueDate") LocalDate dueDate,
            Authentication authentication
    ) {
        User user = (User) authentication.getPrincipal();
        loanService1.createLoan(bookId, user, dueDate);
        return "redirect:/";
    }

    @PostMapping("/{loanId}/approve")
    public String approveLoan(@PathVariable Long loanId) {
        loanService1.handover(loanId, LocalDate.now());
        return "redirect:/admin/loans";
    }



    @PostMapping("/{loanId}/decline")
    public String declineLoan(@PathVariable Long loanId) {
        loanService.markRejected(loanId);
        return "redirect:/admin/loans";
    }

}
