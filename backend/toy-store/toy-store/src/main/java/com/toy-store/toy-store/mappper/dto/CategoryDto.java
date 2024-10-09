package com.petshop.petshop.mappper.dto;

import com.petshop.petshop.model.Product;
import lombok.Builder;

import java.util.List;

@Builder
public record CategoryDto(Long id, String name, List<ProductDto> products) {
}
