package com.example.laba71.service;

import com.example.laba71.dto.ImageDto;
import org.springframework.http.ResponseEntity;

public interface ImageService {
    void uploadImage(ImageDto dto);

    ResponseEntity<?> downloadImageByBookId(Long bookId);
}
