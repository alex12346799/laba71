package com.example.laba71.mapper;


import com.example.laba71.dto.admin.CategoryDto;
import com.example.laba71.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryDto categoryDto) {
        Category category = new Category();
        return Category.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName()).
                build();
    }

    public CategoryDto toDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }
}
