package com.petshop.petshop.mappper.dto;

import com.petshop.petshop.validator.StrongPassword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import javax.validation.constraints.Size;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CredentialsDto {

    @NotNull(message = "Username is mandatory!")
    @NotBlank(message = "Username is mandatory!")
    @Size(min = 2, max = 200, message = "Username must be between 2 and 200 characters.")
    private String username;

    private char[] password;
}