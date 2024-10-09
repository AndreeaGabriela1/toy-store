package com.petshop.petshop.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {
    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {

        if (password == null || password.isEmpty()) {
            return false;
        }

        // password must contain at least one uppercase letter
        boolean containsUppercase = password.matches(".*[A-Z].*");

        // password must contain at least one lowercase letter
        boolean containsLowercase = password.matches(".*[a-z].*");

        // password must contain at least one digit
        boolean containsDigit = password.matches(".*\\d.*");

        // password must contain at least one special character
        boolean containsSpecialChar = password.matches(".*[!@#$%^&*()_+=|\\[\\]{};:,.<>?].*");

        // password must contain at least 3 characters
        boolean containsAtLeast3Char = password.length()>3;

        // Check if all criteria are met
        return containsUppercase && containsLowercase && containsDigit && containsSpecialChar && containsAtLeast3Char;

    }
}
