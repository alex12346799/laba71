package com.example.laba71.service.imp;

import com.example.laba71.dto.ImageDto;
import com.example.laba71.exception.NotFoundException;
import com.example.laba71.model.Book;
import com.example.laba71.repository.BookRepository;
import com.example.laba71.service.ImageService;
import com.example.laba71.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private static final String IMAGE = "images";
    private final BookRepository bookRepository;

    @Override
    public void uploadImage(ImageDto dto) {
        Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new NotFoundException("book id=" + dto.getBookId()));

        String image = FileUtil.saveUploadedFile(dto.getImage(), IMAGE);
        int updated = bookRepository.saveBookImage(image, book.getId());
        if (updated == 0) throw new NotFoundException("book id=" + book.getId() + ", book image not uploaded");
    }

    @Override
    public ResponseEntity<?> downloadImageByBookId(Long bookId) {
        String image = bookRepository.getImageById(bookId)
                .orElseThrow(() -> new NotFoundException("book id=" + bookId));
        return FileUtil.downloadImage(image, IMAGE);
    }
}
