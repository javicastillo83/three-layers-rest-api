package com.carusato.restapi.domain;

/**
 * User domain entity - Plain Old Java Object (POJO).
 * Represents a user in the business domain.
 * No ORM annotations to maintain independence from persistence framework.
 */
public class User {
    
    private final Long id;
    private final String username;
    private final Integer age;
    private final boolean active;
    
    /**
     * Private constructor for immutability.
     * Use builder pattern for object creation.
     */
    private User(Long id, String username, Integer age, boolean active) {
        this.id = id;
        this.username = username;
        this.age = age;
        this.active = active;
    }
    
    /**
     * Gets the user ID.
     * @return the user ID
     */
    public Long getId() {
        return id;
    }
    
    /**
     * Gets the username.
     * @return the username
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * Gets the user age.
     * @return the age
     */
    public Integer getAge() {
        return age;
    }
    
    /**
     * Checks if user is active.
     * @return true if active, false otherwise
     */
    public boolean isActive() {
        return active;
    }
    
    /**
     * Builder for creating User instances.
     * Follows immutability guidelines for domain entities.
     */
    public static class Builder {
        private Long id;
        private String username;
        private Integer age;
        private boolean active = true;
        
        public Builder id(Long id) {
            this.id = id;
            return this;
        }
        
        public Builder username(String username) {
            this.username = username;
            return this;
        }
        
        public Builder age(Integer age) {
            this.age = age;
            return this;
        }
        
        public Builder active(boolean active) {
            this.active = active;
            return this;
        }
        
        public User build() {
            return new User(id, username, age, active);
        }
    }
}

