package com.example.laba71.mappers;

import com.example.laba71.dto.admin.CategoryDto;
import com.example.laba71.dto.admin.CategoryUpsertDto;
import com.example.laba71.model.Category;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = CentralMapperConfig.class)
public interface CategoryMapper {
    CategoryDto toDto(Category e);
    CategoryUpsertDto toUpsertDto(Category e);
    Category toEntity(CategoryUpsertDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget Category target, CategoryUpsertDto dto);
}
