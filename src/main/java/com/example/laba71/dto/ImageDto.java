package com.example.laba71.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageDto {
    @NotNull(message = "Фото обязательно!")
    private MultipartFile image;

    private Long bookId;
}
