package com.carusato.restapi.presentation.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit tests for UpdateUserRequest DTO.
 * Tests validation logic in constructor.
 */
@DisplayName("UpdateUserRequest Unit Tests")
class UpdateUserRequestTest {
    
    @Test
    @DisplayName("Should create request successfully with valid data")
    void shouldCreateRequestSuccessfully() {
        // Arrange
        String username = "jane_doe";
        Integer age = 35;
        
        // Act
        UpdateUserRequest request = new UpdateUserRequest(username, age);
        
        // Assert
        assertThat(request)
                .isNotNull()
                .extracting(UpdateUserRequest::getUsername, UpdateUserRequest::getAge)
                .containsExactly(username, age);
    }
    
    @Test
    @DisplayName("Should throw IllegalArgumentException when username is null")
    void shouldThrowIllegalArgumentExceptionForNullUsername() {
        // Act & Assert
        assertThatThrownBy(() -> new UpdateUserRequest(null, 35))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Username cannot be null or empty");
    }
    
    @Test
    @DisplayName("Should throw IllegalArgumentException when username is blank")
    void shouldThrowIllegalArgumentExceptionForBlankUsername() {
        // Act & Assert
        assertThatThrownBy(() -> new UpdateUserRequest("   ", 35))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Username cannot be null or empty");
    }
    
    @Test
    @DisplayName("Should throw IllegalArgumentException when age is null")
    void shouldThrowIllegalArgumentExceptionForNullAge() {
        // Act & Assert
        assertThatThrownBy(() -> new UpdateUserRequest("jane_doe", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Age must be between 0 and 150");
    }
    
    @Test
    @DisplayName("Should throw IllegalArgumentException when age is negative")
    void shouldThrowIllegalArgumentExceptionForNegativeAge() {
        // Act & Assert
        assertThatThrownBy(() -> new UpdateUserRequest("jane_doe", -10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Age must be between 0 and 150");
    }
    
    @Test
    @DisplayName("Should throw IllegalArgumentException when age is greater than 150")
    void shouldThrowIllegalArgumentExceptionForAgeOver150() {
        // Act & Assert
        assertThatThrownBy(() -> new UpdateUserRequest("jane_doe", 200))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Age must be between 0 and 150");
    }
}

