package com.example.laba71.mapper.impl;

import com.example.laba71.dto.BookListItemDto;
import com.example.laba71.mapper.BookMapper;
import com.example.laba71.model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookMapperImpl implements BookMapper {

    static List<BookListItemDto> toList(Book book) {
        List<BookListItemDto> bookListItemDtos = new ArrayList<>();
        bookListItemDtos.stream()
                .map(Bl -> {
                    Bl.setId(book.getId());
                    Bl.setTitle(book.getTitle());
                    Bl.setAuthor(book.getAuthor());
                    Bl.setImageUrl(book.getImageUrl());
                    Bl.setCategoryName(book.getCategory().getName());

                    return bookListItemDtos;
                }).toList();
        return bookListItemDtos;
    }
}
