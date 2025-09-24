package com.example.laba71.mappers;

import com.example.laba71.dto.BookDto;
import com.example.laba71.model.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDto toDto(Book book);

    Book toEntity(BookDto bookDto);
}