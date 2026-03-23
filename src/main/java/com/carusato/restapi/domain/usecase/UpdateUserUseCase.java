package com.carusato.restapi.domain.usecase;

import com.carusato.restapi.domain.User;

/**
 * Use case for updating a user.
 * 
 * Orchestrates the user update process by delegating to services.
 * Follows the Command Query Responsibility Segregation (CQRS) pattern.
 * 
 * Business Rules:
 * - User must exist
 * - Username must be unique (unless it's the same user)
 * - Age must be between 0 and 150
 */
public interface UpdateUserUseCase {
    
    /**
     * Executes the update user use case.
     * 
     * @param id the user ID
     * @param username the new username
     * @param age the new age
     * @return the updated user
     * @throws IllegalArgumentException if input is invalid
     * @throws com.carusato.restapi.domain.exception.UserNotFoundException if user does not exist
     * @throws com.carusato.restapi.domain.exception.UserAlreadyExistsException if username already exists
     */
    User execute(Long id, String username, Integer age);
}

