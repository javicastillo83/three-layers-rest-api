package com.carusato.restapi.infrastructure.persistence.repository;

import com.carusato.restapi.domain.User;
import com.carusato.restapi.domain.repository.UserRepository;
import com.carusato.restapi.infrastructure.persistence.entity.UserEntity;
import com.carusato.restapi.infrastructure.persistence.mapper.UserEntityMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Implementation of UserRepository interface.
 * Bridges domain layer and data layer using JPA.
 * Converts between domain User and JPA UserEntity.
 */
@Slf4j
@Repository
public class UserRepositoryImpl implements UserRepository {
    
    private final UserJpaRepository userJpaRepository;
    private final UserEntityMapper userEntityMapper;
    
    /**
     * Constructor injection of dependencies.
     * 
     * @param userJpaRepository the JPA repository
     * @param userEntityMapper the entity mapper
     */
    public UserRepositoryImpl(UserJpaRepository userJpaRepository,
                            UserEntityMapper userEntityMapper) {
        this.userJpaRepository = userJpaRepository;
        this.userEntityMapper = userEntityMapper;
    }
    
    /**
     * Saves a domain user to the database.
     * Converts domain User to JPA entity, saves, and converts back.
     * 
     * @param user the domain user to save
     * @return the saved domain user with generated ID
     */
    @Override
    public User save(User user) {
        log.debug("Saving user with username: {}", user.getUsername());
        
        UserEntity entity = userEntityMapper.toEntity(user);
        UserEntity savedEntity = userJpaRepository.save(entity);
        
        return userEntityMapper.toDomain(savedEntity);
    }
    
    /**
     * Finds a user by ID.
     * 
     * @param id the user ID
     * @return Optional containing the user if found
     */
    @Override
    public Optional<User> findById(Long id) {
        log.debug("Finding user by ID: {}", id);
        
        return userJpaRepository.findById(id)
                .map(userEntityMapper::toDomain);
    }
    
    /**
     * Finds a user by username.
     * 
     * @param username the username
     * @return Optional containing the user if found
     */
    @Override
    public Optional<User> findByUsername(String username) {
        log.debug("Finding user by username: {}", username);
        
        return userJpaRepository.findByUsername(username)
                .map(userEntityMapper::toDomain);
    }
    
    /**
     * Deletes a user by ID.
     * 
     * @param id the user ID
     */
    @Override
    public void deleteById(Long id) {
        log.debug("Deleting user by ID: {}", id);
        userJpaRepository.deleteById(id);
    }
    
    /**
     * Checks if a user exists by ID.
     * 
     * @param id the user ID
     * @return true if user exists, false otherwise
     */
    @Override
    public boolean existsById(Long id) {
        log.debug("Checking if user exists by ID: {}", id);
        return userJpaRepository.existsById(id);
    }
}

