package com.example.laba71.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanWithBookDto  {
    private LoanRequestDto loan;
    private BookDto bookInfo;
}
