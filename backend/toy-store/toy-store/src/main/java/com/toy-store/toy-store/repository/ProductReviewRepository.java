package com.petshop.petshop.repository;

import com.petshop.petshop.model.Product;
import com.petshop.petshop.model.ProductReview;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductReviewRepository extends CrudRepository<ProductReview, Long> {
    List<ProductReview> findAll();
    List<ProductReview> findByProduct(Product product);

}
