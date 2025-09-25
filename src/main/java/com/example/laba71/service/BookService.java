package com.example.laba71.service;

import com.example.laba71.dto.BookListItemDto;
import com.example.laba71.dto.PageDto;
import com.example.laba71.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface BookService {
    LocalDate findExpectedAvailableAt(Book book);

    Page<BookListItemDto> search(String q, Integer year, Long categoryId, Pageable pageable);

    PageDto<BookListItemDto> getBookPage(Integer page, Integer size, Long categoryId);

    PageDto<BookListItemDto> getBookPage(
            Integer page, Integer size, Long categoryId,
            String q, Integer year, String sort);

    List<BookListItemDto> getBookList();
}
