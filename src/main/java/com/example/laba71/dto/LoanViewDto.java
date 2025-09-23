package com.example.laba71.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanViewDto {
    private Long loanId;
    private Long bookId;
    private String title;
    private String author;

    private LocalDate borrowDate;
    private LocalDate dueDate;
    private LocalDate returnedAt;

    private String status;
}
