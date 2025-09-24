package com.example.laba71.dto;

import lombok.*;

import java.time.LocalDate;

/**
 * DTO for {@link com.example.laba71.model.Book}
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookListItemDto {
    private Long id;
    private String title;
    private String author;
    private String imageUrl;
    private String categoryName;

    private boolean available;
    private LocalDate expectedAvailableAt;
}
