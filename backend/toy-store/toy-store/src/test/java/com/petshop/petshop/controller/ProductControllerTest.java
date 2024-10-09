package com.petshop.petshop.controller;

import com.petshop.petshop.mappper.dto.ProductDto;
import com.petshop.petshop.model.Product;
import com.petshop.petshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProducts() {
        List<ProductDto> productDtos = new ArrayList<>();
        ProductDto productDto = new ProductDto(1L, "testProduct", "description", "image-url", null, 10.0, 4);
        productDtos.add(productDto);

        when(productService.getAllProductDtos()).thenReturn(productDtos);

        ResponseEntity<List<ProductDto>> responseEntity = productController.getAllProducts();

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(1, responseEntity.getBody().size());
        assertEquals("testProduct", responseEntity.getBody().get(0).getName());
    }

    @Test
    void testCreateProduct() {
        ProductDto productDto = new ProductDto(1L, "testProduct", "description", "image-url", null, 10.0, 4);

        when(productService.createProduct(productDto)).thenReturn(productDto);

        ResponseEntity<ProductDto> responseEntity = productController.createProduct(productDto);

        assertNotNull(responseEntity);
        assertEquals(201, responseEntity.getStatusCodeValue());
        assertEquals("testProduct", responseEntity.getBody().getName());
    }

    @Test
    void testDeleteProduct() {
        ProductDto productDto = new ProductDto(1L, "testProduct", "description", "image-url", null, 10.0, 4);

        when(productService.getProductById(1L)).thenReturn(new Product());

        ResponseEntity<ProductDto> responseEntity = productController.deleteProduct(productDto);

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    void testEditProduct() {
        ProductDto productDto = new ProductDto(1L, "testProduct", "description", "image-url", null, 10.0, 4);

        when(productService.updateProductDto(productDto)).thenReturn(productDto);

        ResponseEntity<ProductDto> responseEntity = productController.editProduct(productDto);

        assertNotNull(responseEntity);
        assertEquals(201, responseEntity.getStatusCodeValue());
    }

    @Test
    void testGetProductById() {
        Long productId = 1L;

        ProductDto productDto = new ProductDto(productId, "testProduct", "description", "image-url", null, 10.0, 4);

        when(productService.getProductDtoById(productId)).thenReturn(productDto);

        ResponseEntity<ProductDto> responseEntity = productController.getProductById(productId);

        assertNotNull(responseEntity);
        assertEquals(201, responseEntity.getStatusCodeValue());
        assertEquals("testProduct", responseEntity.getBody().getName());
    }
}
