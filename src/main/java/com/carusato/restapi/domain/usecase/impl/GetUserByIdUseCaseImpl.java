package com.carusato.restapi.domain.usecase.impl;

import com.carusato.restapi.application.service.UserService;
import com.carusato.restapi.domain.User;
import com.carusato.restapi.domain.usecase.GetUserByIdUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Implementation of GetUserByIdUseCase.
 * Orchestrates the user retrieval process by delegating to UserService.
 */
@Slf4j
@Component
public class GetUserByIdUseCaseImpl implements GetUserByIdUseCase {
    
    private final UserService userService;
    
    /**
     * Constructor injection of UserService.
     * @param userService the user service
     */
    public GetUserByIdUseCaseImpl(UserService userService) {
        this.userService = userService;
    }
    
    /**
     * Executes the get user by ID use case.
     * Orchestrates the retrieval process:
     * 1. Delegates to service to find user
     * 2. Returns Optional with user or empty
     * 
     * @param id the user ID
     * @return Optional containing the user if found
     */
    @Override
    public Optional<User> execute(Long id) {
        log.debug("Executing GetUserByIdUseCase for ID: {}", id);
        
        // Orchestrate the retrieval by delegating to service
        Optional<User> user = userService.getUserById(id);
        
        if (user.isPresent()) {
            log.debug("User found with ID: {}", id);
        } else {
            log.debug("User not found with ID: {}", id);
        }
        
        return user;
    }
}

