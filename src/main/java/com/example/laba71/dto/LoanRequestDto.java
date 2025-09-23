package com.example.laba71.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanRequestDto {
    @NotBlank
    private String libraryCardNumber;

    @NotNull
    private Long bookId;

    @NotNull
    @FutureOrPresent
    private LocalDate dueDate;
}
