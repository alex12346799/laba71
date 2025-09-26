package com.example.laba71.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanRequestAdminDto {
    private Long id;
    private Long bookId;
    private String title;
    private String author;
    private String category;
    private String readerName;
    private String libraryCardNumber;
    private LocalDate requestedDueDate;
    private LocalDateTime createdAt;
}
