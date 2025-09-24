package com.example.laba71.mappers;

import com.example.laba71.dto.BookDto;
import com.example.laba71.dto.BookListItemDto;
import com.example.laba71.dto.BookUpsertDto;
import com.example.laba71.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;

@Mapper(config = CentralMapperConfig.class, uses = CategoryMapper.class)
public interface BookMapper {

    BookDto toDto(Book book);

    @Mapping(target = "categoryId", source = "category.id")
    BookUpsertDto toUpsertDto(Book book);

    @Mapping(target = "category.id", source = "categoryId")
    Book toEntity(BookUpsertDto dto);

    @Mapping(target = "categoryName",
            expression = "java(book.getCategory() != null ? book.getCategory().getName() : null)")
    @Mapping(target = "available",
            expression = "java(book.getAvailableCopies() != null && book.getAvailableCopies() > 0)")
    @Mapping(target = "expectedAvailableAt", expression = "java(expectedAt)")
    BookListItemDto toListItem(Book book, LocalDate expectedAt);
}