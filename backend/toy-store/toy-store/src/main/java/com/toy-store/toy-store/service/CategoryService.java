package com.petshop.petshop.service;

import com.petshop.petshop.mappper.dto.CategoryDto;
import com.petshop.petshop.model.Category;

import java.util.List;

public interface CategoryService {
    public CategoryDto getCategoryDtoById(Long id);

    public Category getCategoryById(Long id);


    public List<CategoryDto> getAllCategoryDtos();

    public List<Category> getAllCategories();

    public CategoryDto createCategoryDto(Category category);

    public CategoryDto updateCategoryDto(CategoryDto categoryDto);

    public Category updateCategory(Category category);

    public void deleteCategory(Category category);
}
