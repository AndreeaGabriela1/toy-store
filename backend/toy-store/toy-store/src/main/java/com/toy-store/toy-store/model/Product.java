package com.petshop.petshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.Size;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "PRODUCTS")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;
    private String imageurl;
    private Double price;
    @ManyToOne
    private Category category;

    @ManyToOne
    private User seller;

    @JsonManagedReference // Indicate that this is the parent side of the relationship
    @Column(nullable = true)
    @OneToMany(mappedBy = "product")
    private List<ProductReview> reviews;


}
