package com.carusato.restapi.domain.usecase;

import com.carusato.restapi.domain.User;

/**
 * Use case for creating a new user.
 * 
 * Orchestrates the user creation process by delegating to services.
 * Follows the Command Query Responsibility Segregation (CQRS) pattern.
 * 
 * Business Rules:
 * - Username must be unique
 * - Username and age are required
 * - Age must be between 0 and 150
 */
public interface CreateUserUseCase {
    
    /**
     * Executes the create user use case.
     * 
     * @param username the username
     * @param age the user age
     * @return the created user
     * @throws IllegalArgumentException if input is invalid
     * @throws com.carusato.restapi.domain.exception.UserAlreadyExistsException if username already exists
     */
    User execute(String username, Integer age);
}

