package com.example.laba71.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanReturnDto {
    @NotNull
    private Long loanId;
    @NotNull private LocalDate returnedAt;
}