package com.carusato.restapi.domain.usecase.impl;

import com.carusato.restapi.application.service.UserService;
import com.carusato.restapi.domain.User;
import com.carusato.restapi.domain.usecase.DeactivateUserUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Implementation of DeactivateUserUseCase.
 * Orchestrates the user deactivation process by delegating to UserService.
 * 
 * Business Rules:
 * - User must exist
 * - Deactivation is a soft delete (user data is preserved)
 */
@Slf4j
@Component
public class DeactivateUserUseCaseImpl implements DeactivateUserUseCase {
    
    private final UserService userService;
    
    /**
     * Constructor injection of UserService.
     * @param userService the user service
     */
    public DeactivateUserUseCaseImpl(UserService userService) {
        this.userService = userService;
    }
    
    /**
     * Executes the deactivate user use case.
     * Orchestrates the deactivation process:
     * 1. Validates user exists (delegated to service)
     * 2. Marks user as inactive (delegated to service)
     * 
     * @param id the user ID
     * @return the deactivated user
     * @throws com.carusato.restapi.domain.exception.UserNotFoundException if user not found
     */
    @Override
    public User execute(Long id) {
        log.info("Executing DeactivateUserUseCase for user ID: {}", id);
        
        // Orchestrate the deactivation by delegating to service
        User deactivatedUser = userService.deactivateUser(id);
        
        log.info("User deactivated successfully with ID: {}", id);
        return deactivatedUser;
    }
}

