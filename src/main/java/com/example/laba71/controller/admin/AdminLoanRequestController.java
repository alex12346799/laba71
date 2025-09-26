package com.example.laba71.controller.admin;

import com.example.laba71.service.LoanRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/requests")
@RequiredArgsConstructor
public class AdminLoanRequestController {
    private final LoanRequestService loanRequestService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("requests", loanRequestService.getPendingRequests());
        return "admin/requests/list";
    }

    @PostMapping("/{id}/approve")
    public String approve(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            loanRequestService.approve(id);
            redirectAttributes.addFlashAttribute("requestMessage", "Заявка одобрена");
        } catch (IllegalArgumentException | IllegalStateException ex) {
            redirectAttributes.addFlashAttribute("requestError", ex.getMessage());
        }
        return "redirect:/admin/requests";
    }

    @PostMapping("/{id}/decline")
    public String decline(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            loanRequestService.decline(id);
            redirectAttributes.addFlashAttribute("requestMessage", "Заявка отклонена");
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("requestError", ex.getMessage());
        }
        return "redirect:/admin/requests";
    }
}
