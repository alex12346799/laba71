package com.example.laba71.controller.api;

import com.example.laba71.dto.ImageDto;
import com.example.laba71.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ImageRestController {
    private final ImageService imageService;

    @PostMapping("/image")
    public ResponseEntity<Void> uploadImage(
            @RequestPart("image") MultipartFile image,
            @RequestPart("bookId") Long bookId) {
        imageService.uploadImage(
                ImageDto
                        .builder()
                        .bookId(bookId)
                        .image(image)
                        .build()
        );
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/image")
    public ResponseEntity<?> downloadImage(Long bookId) {
        return imageService.downloadImageByBookId(bookId);
    }

    @GetMapping("image/{id}")
    public ResponseEntity<?> downloadImageById(@PathVariable Long id) {
        return imageService.downloadImageByBookId(id);
    }
}
