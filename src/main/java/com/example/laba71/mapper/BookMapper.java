package com.example.laba71.mapper;

import com.example.laba71.dto.BookDto;
import com.example.laba71.dto.BookListItemDto;
import com.example.laba71.model.Book;
import com.example.laba71.model.Loan;
import com.example.laba71.model.LoanStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;


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

    public static BookDto toBookDto(Book book, List<Loan> loans) {
        if (book == null) {
            return null;
        }


        boolean isTaken = loans.stream()
                .anyMatch(loan -> loan.getStatus() == LoanStatus.EXPECTED);


        LocalDate expectedAvailableAt = isTaken ? loans.stream()
                .filter(loan -> loan.getStatus() == LoanStatus.EXPECTED)
                .map(Loan::getDueDate)
                .min(LocalDate::compareTo)
                .orElse(null) : null;

        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .expectedAvailableAt(expectedAvailableAt) // null если свободна
                .build();
    }



}
