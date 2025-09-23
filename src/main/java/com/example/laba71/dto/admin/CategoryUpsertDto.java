package com.example.laba71.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryUpsertDto {
    private Long id;

    @NotBlank
    @Size(max = 120)
    private String name;
}