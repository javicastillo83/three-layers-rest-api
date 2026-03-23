package com.carusato.restapi.domain.usecase.impl;

import com.carusato.restapi.application.service.UserService;
import com.carusato.restapi.domain.User;
import com.carusato.restapi.domain.usecase.CreateUserUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Implementation of CreateUserUseCase.
 * Orchestrates the user creation process by delegating to UserService.
 * 
 * Business Rules:
 * - Username must be unique
 * - Username and age are required
 * - Age must be between 0 and 150
 */
@Slf4j
@Component
public class CreateUserUseCaseImpl implements CreateUserUseCase {
    
    private final UserService userService;
    
    /**
     * Constructor injection of UserService.
     * @param userService the user service
     */
    public CreateUserUseCaseImpl(UserService userService) {
        this.userService = userService;
    }
    
    /**
     * Executes the create user use case.
     * Orchestrates the creation process:
     * 1. Validates input (delegated to service)
     * 2. Checks username uniqueness (delegated to service)
     * 3. Creates the user (delegated to service)
     * 
     * @param username the username
     * @param age the user age
     * @return the created user
     * @throws IllegalArgumentException if input is invalid
     * @throws com.carusato.restapi.domain.exception.UserAlreadyExistsException if username already exists
     */
    @Override
    public User execute(String username, Integer age) {
        log.info("Executing CreateUserUseCase for username: {}", username);
        
        // Orchestrate the creation by delegating to service
        User createdUser = userService.createUser(username, age);
        
        log.info("User created successfully with ID: {}", createdUser.getId());
        return createdUser;
    }
}

