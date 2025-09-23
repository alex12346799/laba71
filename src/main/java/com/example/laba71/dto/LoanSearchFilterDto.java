package com.example.laba71.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanSearchFilterDto {
    private String libraryCardNumber;
    private Long bookId;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String status;
    @Min(0) private int page = 0;
    @Min(1)
    @Max(200)
    private int size = 20;
    private String sortBy = "dueDate";
    private String sortDir = "DESC";
}