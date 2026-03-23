package com.carusato.restapi.domain.usecase;

/**
 * Use case for deleting a user.
 * 
 * Orchestrates the user deletion process by delegating to services.
 * Follows the Command Query Responsibility Segregation (CQRS) pattern.
 * 
 * Business Rules:
 * - User must exist
 */
public interface DeleteUserUseCase {
    
    /**
     * Executes the delete user use case.
     * 
     * @param id the user ID
     * @throws com.carusato.restapi.domain.exception.UserNotFoundException if user does not exist
     */
    void execute(Long id);
}
