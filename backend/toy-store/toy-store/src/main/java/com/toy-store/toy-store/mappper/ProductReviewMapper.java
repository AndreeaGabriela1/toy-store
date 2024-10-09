package com.petshop.petshop.mappper;

import com.petshop.petshop.mappper.dto.ProductDto;
import com.petshop.petshop.mappper.dto.ProductReviewDto;
import com.petshop.petshop.model.Product;
import com.petshop.petshop.model.ProductReview;
import com.petshop.petshop.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class ProductReviewMapper {


    public ProductReviewDto productReviewEntityToDto(ProductReview productReview){
        User user = productReview.getUser();
        Product product= productReview.getProduct();
        return ProductReviewDto
                .builder()
                .id(productReview.getId())
                .title(productReview.getTitle())
                .description(productReview.getDescription())
                .stars(productReview.getStars())
                .username(user.getUsername())
                .productId(product.getId())
                .build();
    }

    public List<ProductReviewDto> productReviewListEntitytoDto(List<ProductReview> productList){
        if(productList!=null)
            return productList.stream()
                    .map(product -> productReviewEntityToDto(product))
                    .toList();
        return null;
    }


//    public ProductReview productReviewDtoToEntity(ProductReviewDto productReviewDto){
//
//
//        Product product = productService.getProductById(productReviewDto.getProductId());
//        if(product == null){
//            return null;
//        }
//
//        ProductReviewDto productReview = ProductReview.builder()
//                .title(productReviewDto.getTitle())
//                .description(productReviewDto.getDescription())
//                .stars(productReviewDto.getStars())
//                .product(product)
//                .user(userService.findByUsername(productReviewDto.getUsername()).get())
//                .build();
//
//        return productReview;
//    }

//    public List<Product> productReviewListDtoToEntity(List<ProductDto> productListDto){
//        if(productListDto == null){
//            return  null;
//        }
//        return productListDto.stream()
//                .map(productDto -> productReviewDtoToEntity(productDto))
//                .toList();
//    }
}
