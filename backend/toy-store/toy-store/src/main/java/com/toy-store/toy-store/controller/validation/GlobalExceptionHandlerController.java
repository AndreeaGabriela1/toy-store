package com.petshop.petshop.controller.validation;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public abstract class GlobalExceptionHandlerController {


    @Autowired
    private HttpServletRequest request;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        String url = request.getRequestURI();
        String method = request.getMethod();
        System.out.println("Validation errors occurred in method: " + method + " at URL: " + url);
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
            System.out.println("Validation error for field '" + error.getField() + "': " + error.getDefaultMessage());
        });
        return errors;
    }


}
