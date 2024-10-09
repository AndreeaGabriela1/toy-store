package com.petshop.petshop.mappper;

import com.petshop.petshop.mappper.dto.CategoryDto;
import com.petshop.petshop.model.Category;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class CategoryMapper {

    private final ProductMapper productMapper;

    public CategoryDto categoryEntityToDto(Category category){
        return CategoryDto
                .builder()
                .id(category.getId())
                .name(category.getName())
                .products(productMapper.productListEntitytoDto(category.getProducts()))
                .build();
    }

    public List<CategoryDto> categoryListEntityToDto(List<Category> categoryList){
        return categoryList.stream()
                .map(category -> categoryEntityToDto(category))
                .toList();
    }

    public Category categoryDtoToEntity(CategoryDto categoryDto){
        return Category
                .builder()
                .id(categoryDto.id())
                .name(categoryDto.name())
                .products(productMapper.productListDtoToEntity(categoryDto.products()))
                .build();
    }

    public List<Category> categoryListDtoToEntity(List<CategoryDto> categoryDtoList){
        return categoryDtoList.stream()
                .map(categoryDto -> categoryDtoToEntity(categoryDto))
                .toList();
    }
}
