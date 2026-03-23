package com.carusato.restapi.domain.usecase.impl;

import com.carusato.restapi.application.service.UserService;
import com.carusato.restapi.domain.usecase.DeleteUserUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Implementation of DeleteUserUseCase.
 * Orchestrates the user deletion process by delegating to UserService.
 * 
 * Business Rules:
 * - User must exist
 * - Deletion is permanent (hard delete)
 */
@Slf4j
@Component
public class DeleteUserUseCaseImpl implements DeleteUserUseCase {
    
    private final UserService userService;
    
    /**
     * Constructor injection of UserService.
     * @param userService the user service
     */
    public DeleteUserUseCaseImpl(UserService userService) {
        this.userService = userService;
    }
    
    /**
     * Executes the delete user use case.
     * Orchestrates the deletion process:
     * 1. Validates user exists (delegated to service)
     * 2. Deletes the user (delegated to service)
     * 
     * @param id the user ID
     * @throws com.carusato.restapi.domain.exception.UserNotFoundException if user not found
     */
    @Override
    public void execute(Long id) {
        log.info("Executing DeleteUserUseCase for user ID: {}", id);
        
        // Orchestrate the deletion by delegating to service
        userService.deleteUser(id);
        
        log.info("User deleted successfully with ID: {}", id);
    }
}

