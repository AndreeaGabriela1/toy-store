package com.petshop.petshop.mappper;

import com.petshop.petshop.mappper.dto.ProductDto;
import com.petshop.petshop.model.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class ProductMapper {

    public ProductDto productEntityToDto(Product product){
        return ProductDto
                .builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                //.category(product.getCategory())
                .imageurl(product.getImageurl())
                .price(product.getPrice())
                .build();
    }

    public List<ProductDto> productListEntitytoDto(List<Product> productList){
        if(productList!=null)
            return productList.stream()
                    .map(product -> productEntityToDto(product))
                    .toList();
        return null;
    }

    public Product productDtoToEntity(ProductDto productDto){
        if(productDto == null){
            return  null;
        }
        return Product
                .builder()
                .id(productDto.getId())
                .name(productDto.getName())
                .description(productDto.getDescription())
               // .category(productDto.category())
                .imageurl(productDto.getImageurl())
                .price(productDto.getPrice())
                .build();
    }



    public List<Product> productListDtoToEntity(List<ProductDto> productListDto){
        if(productListDto == null){
            return  null;
        }
        return productListDto.stream()
                .map(productDto -> productDtoToEntity(productDto))
                .toList();
    }
}
