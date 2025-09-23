package com.example.laba71.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookUpsertDto {
    private Long id;

    @NotBlank
    @Size(max = 200)
    private String title;

    @NotBlank
    @Size(max = 200)
    private String author;

    @NotNull
    private Long categoryId;

    @Size(max = 512)
    private String imageUrl;

    @NotNull
    @PositiveOrZero
    private Integer totalCopies;

    @NotNull
    @PositiveOrZero
    private Integer availableCopies;
}
