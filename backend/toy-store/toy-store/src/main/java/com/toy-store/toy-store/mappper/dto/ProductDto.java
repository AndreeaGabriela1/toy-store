package com.petshop.petshop.mappper.dto;

import com.petshop.petshop.model.Category;
import com.petshop.petshop.model.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import javax.validation.constraints.Size;
import java.util.List;

//todo add Category category,  field
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ProductDto{

    private Long id;

    @NotNull(message = "Product Name is mandatory!")
    @NotBlank(message = "Product Name is mandatory!")
    @Size(min = 2, max = 200, message = "Product Name must be between 2 and 200 characters.")
    private String name;

    @NotNull(message = "Description is mandatory!")
    @NotBlank(message = "Description is mandatory!")
    @Size(min = 5, max = 200, message = "Description must be between 5 and 200 characters.")
    private String description;

    private String imageurl;
    private List<String> tags;
    private Double price;
    private Integer stars;

}
