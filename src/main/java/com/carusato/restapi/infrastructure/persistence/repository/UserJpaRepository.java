package com.carusato.restapi.infrastructure.persistence.repository;

import com.carusato.restapi.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for UserEntity.
 * Handles database operations at the data layer.
 */
@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
    
    /**
     * Finds a user entity by username.
     * 
     * @param username the username
     * @return Optional containing the user entity if found
     */
    Optional<UserEntity> findByUsername(String username);
}

