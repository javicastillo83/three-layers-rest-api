package com.carusato.restapi.domain.repository;

import com.carusato.restapi.domain.User;
import java.util.Optional;

/**
 * User repository interface - Data access abstraction.
 * Defined in domain layer to maintain independence from persistence framework.
 * Implementation resides in data layer.
 */
public interface UserRepository {
    
    /**
     * Saves a user to the repository.
     * @param user the user to save
     * @return the saved user with generated ID
     */
    User save(User user);
    
    /**
     * Finds a user by ID.
     * @param id the user ID
     * @return Optional containing the user if found
     */
    Optional<User> findById(Long id);
    
    /**
     * Finds a user by username.
     * @param username the username
     * @return Optional containing the user if found
     */
    Optional<User> findByUsername(String username);
    
    /**
     * Deletes a user by ID.
     * @param id the user ID
     */
    void deleteById(Long id);
    
    /**
     * Checks if a user exists by ID.
     * @param id the user ID
     * @return true if user exists, false otherwise
     */
    boolean existsById(Long id);
}

