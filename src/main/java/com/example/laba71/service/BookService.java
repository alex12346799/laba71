package com.example.laba71.service;

import com.example.laba71.dto.BookListItemDto;
import com.example.laba71.model.User;

import java.util.List;

public interface BookService {
    List<BookListItemDto> getBookList();

    void borrowBook(Long bookId, User user);
}
