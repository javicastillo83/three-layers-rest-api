package com.carusato.restapi.domain.usecase.impl;

import com.carusato.restapi.application.service.UserService;
import com.carusato.restapi.domain.User;
import com.carusato.restapi.domain.usecase.GetUserByUsernameUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Implementation of GetUserByUsernameUseCase.
 * Orchestrates the user retrieval process by delegating to UserService.
 */
@Slf4j
@Component
public class GetUserByUsernameUseCaseImpl implements GetUserByUsernameUseCase {
    
    private final UserService userService;
    
    /**
     * Constructor injection of UserService.
     * @param userService the user service
     */
    public GetUserByUsernameUseCaseImpl(UserService userService) {
        this.userService = userService;
    }
    
    /**
     * Executes the get user by username use case.
     * Orchestrates the retrieval process:
     * 1. Delegates to service to find user
     * 2. Returns Optional with user or empty
     * 
     * @param username the username
     * @return Optional containing the user if found
     */
    @Override
    public Optional<User> execute(String username) {
        log.debug("Executing GetUserByUsernameUseCase for username: {}", username);
        
        // Orchestrate the retrieval by delegating to service
        Optional<User> user = userService.getUserByUsername(username);
        
        if (user.isPresent()) {
            log.debug("User found with username: {}", username);
        } else {
            log.debug("User not found with username: {}", username);
        }
        
        return user;
    }
}

