package com.petshop.petshop.service;

import com.petshop.petshop.mappper.dto.ProductDto;
import com.petshop.petshop.model.Category;
import com.petshop.petshop.model.Product;

import java.util.List;

public interface ProductService {
    public ProductDto getProductDtoById(Long id);

    public Product getProductById(Long id);


    public List<ProductDto> getAllProductDtos();

    public List<Product> getAllProducts();

    public ProductDto createProductDto(Product category);
    public ProductDto createProduct(ProductDto product);

    public ProductDto updateProductDto(ProductDto productDto);

    public List<Category> findAllCategory();

    public Product updateProduct(Product product);
    public void deleteProduct(Product category);

}
