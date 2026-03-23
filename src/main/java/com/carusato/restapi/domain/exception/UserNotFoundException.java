package com.carusato.restapi.domain.exception;

/**
 * Exception thrown when a user is not found.
 * Specific business exception for domain layer.
 */
public class UserNotFoundException extends RuntimeException {
    
    /**
     * Constructor with user ID.
     * @param userId the user ID that was not found
     */
    public UserNotFoundException(Long userId) {
        super("User not found with ID: " + userId);
    }
    
    /**
     * Constructor with custom message.
     * @param message the error message
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}

