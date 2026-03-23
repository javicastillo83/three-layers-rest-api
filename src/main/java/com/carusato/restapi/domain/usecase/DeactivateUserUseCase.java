package com.carusato.restapi.domain.usecase;

import com.carusato.restapi.domain.User;

/**
 * Use case for deactivating a user.
 * 
 * Orchestrates the user deactivation process by delegating to services.
 * Follows the Command Query Responsibility Segregation (CQRS) pattern.
 * 
 * Business Rules:
 * - User must exist
 * - Already deactivated users cannot be deactivated again
 */
public interface DeactivateUserUseCase {
    
    /**
     * Executes the deactivate user use case.
     * 
     * @param id the user ID
     * @return the deactivated user
     * @throws com.carusato.restapi.domain.exception.UserNotFoundException if user does not exist
     * @throws IllegalArgumentException if user is already deactivated
     */
    User execute(Long id);
}

