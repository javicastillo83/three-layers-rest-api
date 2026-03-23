package com.carusato.restapi.domain.usecase.impl;

import com.carusato.restapi.application.service.UserService;
import com.carusato.restapi.domain.User;
import com.carusato.restapi.domain.usecase.UpdateUserUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Implementation of UpdateUserUseCase.
 * Orchestrates the user update process by delegating to UserService.
 * 
 * Business Rules:
 * - User must exist
 * - Username cannot be changed to an existing one
 * - Username and age are required
 * - Age must be between 0 and 150
 */
@Slf4j
@Component
public class UpdateUserUseCaseImpl implements UpdateUserUseCase {
    
    private final UserService userService;
    
    /**
     * Constructor injection of UserService.
     * @param userService the user service
     */
    public UpdateUserUseCaseImpl(UserService userService) {
        this.userService = userService;
    }
    
    /**
     * Executes the update user use case.
     * Orchestrates the update process:
     * 1. Validates user exists (delegated to service)
     * 2. Validates input (delegated to service)
     * 3. Checks username uniqueness if changed (delegated to service)
     * 4. Updates the user (delegated to service)
     * 
     * @param id the user ID
     * @param username the new username
     * @param age the new age
     * @return the updated user
     * @throws com.carusato.restapi.domain.exception.UserNotFoundException if user not found
     * @throws com.carusato.restapi.domain.exception.UserAlreadyExistsException if new username already exists
     * @throws IllegalArgumentException if input is invalid
     */
    @Override
    public User execute(Long id, String username, Integer age) {
        log.info("Executing UpdateUserUseCase for user ID: {}", id);
        
        // Orchestrate the update by delegating to service
        User updatedUser = userService.updateUser(id, username, age);
        
        log.info("User updated successfully with ID: {}", id);
        return updatedUser;
    }
}

