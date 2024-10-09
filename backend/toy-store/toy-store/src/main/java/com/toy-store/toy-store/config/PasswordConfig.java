package com.petshop.petshop.config;

import com.petshop.petshop.service.impl.CustomPasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {


        //return new BCryptPasswordEncoder();

        return new CustomPasswordEncoder();
    }
}