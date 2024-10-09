package com.petshop.petshop.service;

import com.petshop.petshop.mappper.dto.ProductReviewDto;
import com.petshop.petshop.model.Product;
import com.petshop.petshop.model.ProductReview;
import java.util.List;

public interface ProductReviewService {

    public ProductReview createReview(ProductReviewDto productReviewDto);
    public void deleteReview(ProductReview productReview);
    public ProductReview editReview(ProductReview productReview);
    public List<ProductReview> getAllReviews();
    public List<ProductReview> getReviewsByProduct(Product product);

}
