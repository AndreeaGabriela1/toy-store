package com.petshop.petshop.model;


import lombok.Data;

import java.util.List;

@Data
public class RegistrationRequest {
    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private String email_address;

    private List<Role> roles;
}