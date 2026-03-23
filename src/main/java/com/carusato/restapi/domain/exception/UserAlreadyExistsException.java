package com.carusato.restapi.domain.exception;

/**
 * Exception thrown when a username already exists.
 * Specific business exception for domain layer.
 */
public class UserAlreadyExistsException extends RuntimeException {
    
    /**
     * Constructor with username.
     * @param username the username that already exists
     */
    public UserAlreadyExistsException(String username) {
        super("User already exists with username: " + username);
    }
}

