package com.carusato.restapi.domain.usecase;

import com.carusato.restapi.domain.User;
import java.util.Optional;

/**
 * Use case for retrieving a user by ID.
 * 
 * Orchestrates the user retrieval process by delegating to services.
 * Follows the Command Query Responsibility Segregation (CQRS) pattern.
 */
public interface GetUserByIdUseCase {
    
    /**
     * Executes the get user by ID use case.
     * 
     * @param id the user ID
     * @return Optional containing the user if found, empty otherwise
     */
    Optional<User> execute(Long id);
}

