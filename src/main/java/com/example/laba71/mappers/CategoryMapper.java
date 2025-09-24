package com.example.laba71.mappers;

import com.example.laba71.dto.admin.CategoryDto;
import com.example.laba71.dto.admin.CategoryUpsertDto;
import com.example.laba71.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toDto(Category e);
    CategoryUpsertDto toUpsertDto(Category e);
    Category toEntity(CategoryUpsertDto dto);
}
