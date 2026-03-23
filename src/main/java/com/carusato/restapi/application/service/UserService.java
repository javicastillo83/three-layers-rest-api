package com.carusato.restapi.application.service;

import com.carusato.restapi.domain.User;
import com.carusato.restapi.domain.exception.UserAlreadyExistsException;
import com.carusato.restapi.domain.exception.UserNotFoundException;
import com.carusato.restapi.domain.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * User service implementing business logic and use cases.
 * 
 * Responsibilities:
 * - Orchestrates user operations (CRUD)
 * - Enforces business rules and validations
 * - Delegates data access to repository
 * 
 * Uses constructor injection for dependency management.
 */
@Slf4j
@Service
public class UserService {
    
    private final UserRepository userRepository;
    
    /**
     * Constructor injection of UserRepository.
     * @param userRepository the user repository implementation
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    /**
     * Creates a new user with validation.
     * Business rule: Username must be unique.
     * 
     * @param username the username
     * @param age the user age
     * @return the created user
     * @throws IllegalArgumentException if username or age is invalid
     * @throws UserAlreadyExistsException if username already exists
     */
    public User createUser(String username, Integer age) {
        log.info("Creating new user with username: {}", username);
        
        validateUserInput(username, age);
        validateUsernameUniqueness(username);
        
        User user = new User.Builder()
                .username(username)
                .age(age)
                .active(true)
                .build();
        
        User savedUser = userRepository.save(user);
        log.info("User created successfully with ID: {}", savedUser.getId());
        
        return savedUser;
    }
    
    /**
     * Retrieves a user by ID.
     * 
     * @param id the user ID
     * @return Optional containing the user if found
     */
    public Optional<User> getUserById(Long id) {
        log.debug("Fetching user with ID: {}", id);
        return userRepository.findById(id);
    }
    
    /**
     * Retrieves a user by username.
     * 
     * @param username the username
     * @return Optional containing the user if found
     */
    public Optional<User> getUserByUsername(String username) {
        log.debug("Fetching user with username: {}", username);
        return userRepository.findByUsername(username);
    }
    
    /**
     * Updates a user's information.
     * Business rule: Username cannot be changed to an existing one.
     * 
     * @param id the user ID
     * @param username the new username
     * @param age the new age
     * @return the updated user
     * @throws UserNotFoundException if user not found
     * @throws UserAlreadyExistsException if new username already exists
     * @throws IllegalArgumentException if input is invalid
     */
    public User updateUser(Long id, String username, Integer age) {
        log.info("Updating user with ID: {}", id);
        
        validateUserInput(username, age);
        
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        
        // Validate username uniqueness only if username has changed
        if (!existingUser.getUsername().equals(username)) {
            validateUsernameUniqueness(username);
        }
        
        User updatedUser = new User.Builder()
                .id(id)
                .username(username)
                .age(age)
                .active(existingUser.isActive())
                .build();
        
        User saved = userRepository.save(updatedUser);
        log.info("User updated successfully with ID: {}", id);
        
        return saved;
    }
    
    /**
     * Deactivates a user by ID.
     * Soft delete pattern: marks as inactive instead of removing.
     * 
     * @param id the user ID
     * @return the deactivated user
     * @throws UserNotFoundException if user not found
     */
    public User deactivateUser(Long id) {
        log.info("Deactivating user with ID: {}", id);
        
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        
        User inactiveUser = new User.Builder()
                .id(id)
                .username(existingUser.getUsername())
                .age(existingUser.getAge())
                .active(false)
                .build();
        
        User saved = userRepository.save(inactiveUser);
        log.info("User deactivated successfully with ID: {}", id);
        
        return saved;
    }
    
    /**
     * Deletes a user by ID.
     * 
     * @param id the user ID
     * @throws UserNotFoundException if user not found
     */
    public void deleteUser(Long id) {
        log.info("Deleting user with ID: {}", id);
        
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        
        userRepository.deleteById(id);
        log.info("User deleted successfully with ID: {}", id);
    }
    
    /**
     * Validates user input data.
     * DRY principle: centralized validation logic.
     * 
     * @param username the username
     * @param age the age
     * @throws IllegalArgumentException if validation fails
     */
    private void validateUserInput(String username, Integer age) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        
        if (age == null || age < 0 || age > 150) {
            throw new IllegalArgumentException("Age must be between 0 and 150");
        }
    }
    
    /**
     * Validates that username is unique.
     * 
     * @param username the username to validate
     * @throws UserAlreadyExistsException if username already exists
     */
    private void validateUsernameUniqueness(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UserAlreadyExistsException(username);
        }
    }
}

