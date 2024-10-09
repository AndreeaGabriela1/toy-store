package com.petshop.petshop.mappper.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ReceiptDto {

    @NotNull(message = "First Name is mandatory!")
    @NotBlank(message = "First Name is mandatory!")
    private String firstName;

    @NotNull(message = "Last Name is mandatory!")
    @NotBlank(message = "Last Name is mandatory!")
    private String lastName;

    @NotNull(message = "Username is mandatory!")
    @NotBlank(message = "Username is mandatory!")
    private String username;

    private List<String> product;


    private String date;

}