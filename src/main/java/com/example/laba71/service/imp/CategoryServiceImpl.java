package com.example.laba71.service.imp;

import com.example.laba71.dto.admin.CategoryDto;
import com.example.laba71.mapper.CategoryMapper;
import com.example.laba71.model.Category;
import com.example.laba71.repository.CategoryRepository;
import com.example.laba71.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;


    @Override
    public List<CategoryDto> getAll() {
        return categoryRepository.findAll()
                .stream().map(categoryMapper::toDto).collect(Collectors.toList());
    }
}
