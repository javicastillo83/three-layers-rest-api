package com.carusato.restapi.domain.usecase;

import com.carusato.restapi.domain.User;
import java.util.Optional;

/**
 * Use case for retrieving a user by username.
 * 
 * Orchestrates the user retrieval process by delegating to services.
 * Follows the Command Query Responsibility Segregation (CQRS) pattern.
 */
public interface GetUserByUsernameUseCase {
    
    /**
     * Executes the get user by username use case.
     * 
     * @param username the username
     * @return Optional containing the user if found, empty otherwise
     */
    Optional<User> execute(String username);
}

