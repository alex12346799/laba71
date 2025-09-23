package com.example.laba71.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanAdminUpdateDto {
    @NotNull
    private Long loanId;
    @NotNull
    private Long bookId;
    @NotNull
    private Long userId;
    @NotNull
    private LocalDate borrowDate;
    @NotNull
    private LocalDate dueDate;
    private LocalDate returnedAt;
    @NotBlank
    private String status;
}