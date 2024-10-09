package com.petshop.petshop.controller;


import com.petshop.petshop.controller.validation.GlobalExceptionHandlerController;
import com.petshop.petshop.mappper.ProductReviewMapper;
import com.petshop.petshop.mappper.dto.ProductDto;
import com.petshop.petshop.mappper.dto.ProductReviewDto;
import com.petshop.petshop.model.Product;
import com.petshop.petshop.model.ProductReview;
import com.petshop.petshop.service.ProductReviewService;
import com.petshop.petshop.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Controller
@AllArgsConstructor
public class ProductReviewController extends GlobalExceptionHandlerController {

    private final ProductReviewService productReviewService;
    private final ProductService productService;
    private final ProductReviewMapper productReviewMapper;

    @PostMapping("/review-create")
    public ResponseEntity<ProductReview> createProductReview(@RequestBody(required = false) @Valid ProductReviewDto productReviewDto) {

        System.out.println("in ProductREviewController!!!!---> " + productReviewDto.getTitle());
        System.out.println("in ProductREviewController!!!!---> " + productReviewDto.getDescription());
        System.out.println("in ProductREviewController!!!!---> " + productReviewDto.getStars());
        System.out.println("in ProductREviewController!!!!---> " + productReviewDto.getProductId());
        System.out.println("in ProductREviewController!!!!---> " + productReviewDto.getUsername());

        ProductReview createdProductReview = productReviewService.createReview(productReviewDto);
        return ResponseEntity.created(URI.create("/product-create" + createdProductReview.getId())).body(createdProductReview);
    }

    @PostMapping("/review-id")
    public ResponseEntity<List<ProductReviewDto>> getAllReviewsByProductId(@RequestBody(required = false) @Valid Long id) {

        if (id == 0){
            System.out.println( "ID NULL IN GET-ALL-REVIEWS");
            return null;
        }

        Product product = productService.getProductById(id);

        if(product == null){
            System.out.println( "PRODUCT NULL IN GET-ALL-REVIEWS");

            return null;
        }

        List<ProductReview> createdProductReviews = productReviewService.getReviewsByProduct(product);

        for(ProductReview p : createdProductReviews){
            System.out.println("--> " + p);
        }

        List<ProductReviewDto> createdProductReviewsDto = productReviewMapper.productReviewListEntitytoDto(createdProductReviews);
        return ResponseEntity.ok(createdProductReviewsDto);
    }
}
