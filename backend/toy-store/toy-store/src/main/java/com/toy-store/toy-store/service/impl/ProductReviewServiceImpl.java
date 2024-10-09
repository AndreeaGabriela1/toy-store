package com.petshop.petshop.service.impl;

import com.petshop.petshop.mappper.dto.ProductReviewDto;
import com.petshop.petshop.model.Product;
import com.petshop.petshop.model.ProductReview;
import com.petshop.petshop.repository.ProductReviewRepository;
import com.petshop.petshop.repository.UserRepository;
import com.petshop.petshop.service.ProductReviewService;
import com.petshop.petshop.service.ProductService;
import com.petshop.petshop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class ProductReviewServiceImpl implements ProductReviewService {

    private final ProductReviewRepository productReviewRepository;

    private final UserService userService;
    ProductService productService;


    @Override
    public ProductReview createReview(ProductReviewDto productReviewDto) {

        //todo: create mappers


        System.out.println("hello!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!S");
        if(productReviewDto.getUsername() == null || productReviewDto.getProductId() == null){
            return null;
        }

        Product product = productService.getProductById(productReviewDto.getProductId());
        if(product == null){
            return null;
        }

        ProductReview productReview = ProductReview.builder()
                .title(productReviewDto.getTitle())
                .description(productReviewDto.getDescription())
                .stars(productReviewDto.getStars())
                .product(product)
                .user(userService.findByUsername(productReviewDto.getUsername()).get())
                .build();
        return productReviewRepository.save(productReview);
    }

    @Override
    public void deleteReview(ProductReview productReview) {
        productReviewRepository.delete(productReview);
    }

    @Override
    public ProductReview editReview(ProductReview productReview) {
        return productReviewRepository.save(productReview);
    }

    @Override
    public List<ProductReview> getAllReviews() {
        return productReviewRepository.findAll();
    }

    @Override
    public List<ProductReview> getReviewsByProduct(Product product) {
        return productReviewRepository.findByProduct(product);
    }
}
