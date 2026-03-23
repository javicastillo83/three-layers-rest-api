package com.carusato.restapi.infrastructure.persistence.mapper;

import com.carusato.restapi.domain.User;
import com.carusato.restapi.infrastructure.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between domain User and JPA UserEntity.
 * Handles conversion between layers without using MapStruct (simple mapping).
 * Can be replaced with MapStruct for complex mappings.
 */
@Component
public class UserEntityMapper {
    
    /**
     * Maps a domain User to a JPA UserEntity.
     * 
     * @param user the domain user
     * @return the JPA user entity
     */
    public UserEntity toEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setUsername(user.getUsername());
        entity.setAge(user.getAge());
        entity.setActive(user.isActive());
        return entity;
    }
    
    /**
     * Maps a JPA UserEntity to a domain User.
     * 
     * @param entity the JPA user entity
     * @return the domain user
     */
    public User toDomain(UserEntity entity) {
        return new User.Builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .age(entity.getAge())
                .active(entity.isActive())
                .build();
    }
}

