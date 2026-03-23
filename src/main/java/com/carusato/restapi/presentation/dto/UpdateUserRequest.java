package com.carusato.restapi.presentation.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO for updating a user.
 * Immutable object with validation in constructor.
 * Validates JSR303/Jakarta validation annotations.
 */
public class UpdateUserRequest {
    
    private final String username;
    private final Integer age;
    
    /**
     * Constructor with validation.
     * Throws exceptions if data is invalid.
     * 
     * @param username the username
     * @param age the age
     * @throws IllegalArgumentException if validation fails
     */
    public UpdateUserRequest(
            @NotBlank(message = "Username cannot be null or empty")
            String username,
            @Min(value = 0, message = "Age must be at least 0")
            Integer age) {
        
        validateInput(username, age);
        
        this.username = username;
        this.age = age;
    }
    
    /**
     * Gets the username.
     * @return the username
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * Gets the age.
     * @return the age
     */
    public Integer getAge() {
        return age;
    }
    
    /**
     * Validates input data.
     * Constructor responsibility, not framework-dependent.
     * 
     * @param username the username
     * @param age the age
     * @throws IllegalArgumentException if validation fails
     */
    private void validateInput(String username, Integer age) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        
        if (age == null || age < 0 || age > 150) {
            throw new IllegalArgumentException("Age must be between 0 and 150");
        }
    }
}

