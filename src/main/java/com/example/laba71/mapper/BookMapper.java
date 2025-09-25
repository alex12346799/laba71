package com.example.laba71.mapper;

import com.example.laba71.dto.BookListItemDto;
import com.example.laba71.model.Book;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component
public class BookMapper {

    public BookListItemDto toListItemDto(Book book) {
        return toListItemDto(book, null);
    }


    public BookListItemDto toListItemDto(Book book, LocalDate expectedAvailableAt) {
        boolean available = book.getAvailableCopies() != null && book.getAvailableCopies() > 0;

        return BookListItemDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .imageUrl(book.getImageUrl())
                .publicationYear(book.getPublicationYear())
                .categoryName(book.getCategory() != null ? book.getCategory().getName() : null)
                .available(available)
                .totalCopies(book.getTotalCopies())
                .availableCopies(book.getAvailableCopies())
                .expectedAvailableAt(expectedAvailableAt)
                .build();
    }

    public BookListItemDto toDto(Book book, LocalDate expectedAvailableAt) {
        return BookListItemDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .imageUrl(book.getImageUrl())
                .publicationYear(book.getPublicationYear())
                .categoryName(book.getCategory() != null ? book.getCategory().getName() : null)
                .available(book.getAvailableCopies() != null && book.getAvailableCopies() > 0)
                .expectedAvailableAt(expectedAvailableAt)
                .build();
    }
}
