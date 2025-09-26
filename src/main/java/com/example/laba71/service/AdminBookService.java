package com.example.laba71.service;

import com.example.laba71.dto.BookListItemDto;
import com.example.laba71.dto.PageDto;

public interface AdminBookService {
    PageDto<BookListItemDto> findBooks(String q, Integer year, Long categoryId,
                                       int page, int size);
}
