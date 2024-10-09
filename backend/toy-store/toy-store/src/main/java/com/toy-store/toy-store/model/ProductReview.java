package com.petshop.petshop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "PRODUCTS_REVIEW")
public class ProductReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private Integer stars;

    @JsonBackReference
    @ManyToOne
    //@JsonIgnoreProperties("reviews") // Ignore the "reviews" field of Product during serialization
    private Product product;

    @ManyToOne
    private User user;

}
