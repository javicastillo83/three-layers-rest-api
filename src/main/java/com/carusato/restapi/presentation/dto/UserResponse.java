package com.carusato.restapi.presentation.dto;

import lombok.Getter;

/**
 * DTO for user response.
 * Immutable response object for API endpoints.
 */
@Getter
public class UserResponse {
    
    private final Long id;
    private final String username;
    private final Integer age;
    private final boolean active;
    
    /**
     * Constructor for UserResponse.
     * @param id the user ID
     * @param username the username
     * @param age the age
     * @param active whether user is active
     */
    public UserResponse(Long id, String username, Integer age, boolean active) {
        this.id = id;
        this.username = username;
        this.age = age;
        this.active = active;
    }
}

