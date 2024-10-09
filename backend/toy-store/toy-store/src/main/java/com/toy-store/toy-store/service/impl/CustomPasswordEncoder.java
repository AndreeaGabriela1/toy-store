package com.petshop.petshop.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import java.security.MessageDigest;
import java.util.Objects;
import java.nio.charset.StandardCharsets;


public class CustomPasswordEncoder implements PasswordEncoder {

    private static final String FIXED_SALT = "YourFixedSaltValue"; // Use a fixed salt value

    @Override
    public String encode(CharSequence rawPassword) {
        // Generate salt and encode password
        String password = rawPassword.toString();

        return hashPassword(password);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        // Since we're using deterministic hashing, we'll re-hash the raw password and compare
        String password = rawPassword.toString();

        String hashedPassword = hashPassword(password);
        return Objects.equals(hashedPassword, encodedPassword);
    }

    private String hashPassword(String password) {
        try {
            // Sercured Hash Algorithm - 256
            // 1 byte = 8 biți
            // 1 byte = 1 char
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}