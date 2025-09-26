package com.example.laba71.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDto {
    private Long id;
    private String author;
    private String title;
    private LocalDate expectedAvailableAt;
}
