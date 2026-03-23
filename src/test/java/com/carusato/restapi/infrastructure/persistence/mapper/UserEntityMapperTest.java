package com.carusato.restapi.infrastructure.persistence.mapper;

import com.carusato.restapi.domain.User;
import com.carusato.restapi.infrastructure.persistence.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for UserEntityMapper.
 * Tests mapping between domain User and JPA UserEntity.
 */
@DisplayName("UserEntityMapper Unit Tests")
class UserEntityMapperTest {
    
    private UserEntityMapper mapper;
    
    @BeforeEach
    void setUp() {
        mapper = new UserEntityMapper();
    }
    
    // ============ toEntity Tests ============
    
    @Test
    @DisplayName("Should map User to UserEntity successfully")
    void shouldMapUserToEntitySuccessfully() {
        // Arrange
        User user = new User.Builder()
                .id(1L)
                .username("john_doe")
                .age(30)
                .active(true)
                .build();
        
        // Act
        UserEntity entity = mapper.toEntity(user);
        
        // Assert
        assertThat(entity)
                .isNotNull()
                .extracting(UserEntity::getId, UserEntity::getUsername, UserEntity::getAge, UserEntity::isActive)
                .containsExactly(1L, "john_doe", 30, true);
    }
    
    @Test
    @DisplayName("Should map User with null ID to UserEntity")
    void shouldMapUserWithNullIdToEntity() {
        // Arrange
        User user = new User.Builder()
                .username("jane_doe")
                .age(28)
                .active(true)
                .build();
        
        // Act
        UserEntity entity = mapper.toEntity(user);
        
        // Assert
        assertThat(entity)
                .isNotNull()
                .extracting(UserEntity::getId, UserEntity::getUsername, UserEntity::getAge)
                .containsExactly(null, "jane_doe", 28);
    }
    
    @Test
    @DisplayName("Should map inactive User to UserEntity")
    void shouldMapInactiveUserToEntity() {
        // Arrange
        User user = new User.Builder()
                .id(1L)
                .username("john_doe")
                .age(30)
                .active(false)
                .build();
        
        // Act
        UserEntity entity = mapper.toEntity(user);
        
        // Assert
        assertThat(entity.isActive()).isFalse();
    }
    
    // ============ toDomain Tests ============
    
    @Test
    @DisplayName("Should map UserEntity to User successfully")
    void shouldMapEntityToUserSuccessfully() {
        // Arrange
        UserEntity entity = new UserEntity(1L, "john_doe", 30, true);
        
        // Act
        User user = mapper.toDomain(entity);
        
        // Assert
        assertThat(user)
                .isNotNull()
                .extracting(User::getId, User::getUsername, User::getAge, User::isActive)
                .containsExactly(1L, "john_doe", 30, true);
    }
    
    @Test
    @DisplayName("Should map UserEntity to immutable User")
    void shouldMapEntityToImmutableUser() {
        // Arrange
        UserEntity entity = new UserEntity(2L, "jane_doe", 25, true);
        
        // Act
        User user = mapper.toDomain(entity);
        
        // Assert
        assertThat(user)
                .isNotNull()
                .hasFieldOrPropertyWithValue("id", 2L)
                .hasFieldOrPropertyWithValue("username", "jane_doe")
                .hasFieldOrPropertyWithValue("age", 25);
    }
    
    @Test
    @DisplayName("Should map inactive UserEntity to User")
    void shouldMapInactiveEntityToUser() {
        // Arrange
        UserEntity entity = new UserEntity(1L, "john_doe", 30, false);
        
        // Act
        User user = mapper.toDomain(entity);
        
        // Assert
        assertThat(user.isActive()).isFalse();
    }
    
    // ============ Round-trip Tests ============
    
    @Test
    @DisplayName("Should maintain data integrity in User -> Entity -> User round trip")
    void shouldMaintainDataIntegrityInRoundTrip() {
        // Arrange
        User originalUser = new User.Builder()
                .id(1L)
                .username("john_doe")
                .age(30)
                .active(true)
                .build();
        
        // Act
        UserEntity entity = mapper.toEntity(originalUser);
        User resultingUser = mapper.toDomain(entity);
        
        // Assert
        assertThat(resultingUser)
                .extracting(User::getId, User::getUsername, User::getAge, User::isActive)
                .containsExactly(
                        originalUser.getId(),
                        originalUser.getUsername(),
                        originalUser.getAge(),
                        originalUser.isActive()
                );
    }
    
    @Test
    @DisplayName("Should maintain data integrity in Entity -> User -> Entity round trip")
    void shouldMaintainDataIntegrityInReverseRoundTrip() {
        // Arrange
        UserEntity originalEntity = new UserEntity(1L, "jane_doe", 28, true);
        
        // Act
        User user = mapper.toDomain(originalEntity);
        UserEntity resultingEntity = mapper.toEntity(user);
        
        // Assert
        assertThat(resultingEntity)
                .extracting(UserEntity::getId, UserEntity::getUsername, UserEntity::getAge, UserEntity::isActive)
                .containsExactly(
                        originalEntity.getId(),
                        originalEntity.getUsername(),
                        originalEntity.getAge(),
                        originalEntity.isActive()
                );
    }
}

