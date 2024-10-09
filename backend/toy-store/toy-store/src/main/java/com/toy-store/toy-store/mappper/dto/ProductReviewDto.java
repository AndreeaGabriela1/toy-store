package com.petshop.petshop.mappper.dto;

import com.petshop.petshop.model.Product;
import com.petshop.petshop.model.User;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ProductReviewDto {

    private Long id;
    private String title;
    private String description;
    private Integer stars;
    private Long productId;
    private String username;
}
