package com.petshop.petshop.service.impl;

import com.petshop.petshop.mappper.CategoryMapper;
import com.petshop.petshop.mappper.dto.CategoryDto;
import com.petshop.petshop.model.Category;
import com.petshop.petshop.repository.CategoryRepository;
import com.petshop.petshop.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto getCategoryDtoById(Long id) {
        return categoryMapper.categoryEntityToDto(categoryRepository.findById(id).orElse(null));
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public List<CategoryDto> getAllCategoryDtos() {
        return categoryMapper.categoryListEntityToDto(categoryRepository.findAll());
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public CategoryDto createCategoryDto(Category category) {
        return categoryMapper.categoryEntityToDto(categoryRepository.save(category));
    }

    @Override
    public CategoryDto updateCategoryDto(CategoryDto categoryDto) {
        Category category = categoryMapper.categoryDtoToEntity(categoryDto);
        CategoryDto categoryDtoSaved = categoryMapper.categoryEntityToDto(categoryRepository.save(category));
        return categoryDtoSaved;    }



    @Override
    public Category updateCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Category category) {
        categoryRepository.delete(category);
    }
}
