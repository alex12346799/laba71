package com.example.laba71.controller;

import com.example.laba71.dto.LoanRequestDto;
import com.example.laba71.service.LoanRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final LoanRequestService loanRequestService;

    @PostMapping("/books/{id}/request")
    public String requestBook(@PathVariable Long id,
                              @Valid @ModelAttribute("loanRequestDto") LoanRequestDto loanRequestDto,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
        loanRequestDto.setBookId(id);

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("loanRequestDto", loanRequestDto);
            redirectAttributes.addFlashAttribute("loanRequestError", "Проверьте корректность введённых данных");
            redirectAttributes.addFlashAttribute("loanRequestErrorBookId", id);
            return "redirect:/";
        }

        try {
            loanRequestService.createRequest(id, loanRequestDto.getLibraryCardNumber(), loanRequestDto.getDueDate());
            redirectAttributes.addFlashAttribute("loanRequestSuccess", "Заявка успешно отправлена администратору");
            redirectAttributes.addFlashAttribute("loanRequestSuccessBookId", id);
        } catch (IllegalArgumentException | IllegalStateException ex) {
            redirectAttributes.addFlashAttribute("loanRequestDto", loanRequestDto);
            redirectAttributes.addFlashAttribute("loanRequestError", ex.getMessage());
            redirectAttributes.addFlashAttribute("loanRequestErrorBookId", id);
        }

        return "redirect:/";
    }
}
