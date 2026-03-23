package com.carusato.restapi.presentation.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit tests for CreateUserRequest DTO.
 * Tests validation logic in constructor.
 */
@DisplayName("CreateUserRequest Unit Tests")
class CreateUserRequestTest {
    
    @Test
    @DisplayName("Should create request successfully with valid data")
    void shouldCreateRequestSuccessfully() {
        // Arrange
        String username = "john_doe";
        Integer age = 30;
        
        // Act
        CreateUserRequest request = new CreateUserRequest(username, age);
        
        // Assert
        assertThat(request)
                .isNotNull()
                .extracting(CreateUserRequest::getUsername, CreateUserRequest::getAge)
                .containsExactly(username, age);
    }
    
    @Test
    @DisplayName("Should throw IllegalArgumentException when username is null")
    void shouldThrowIllegalArgumentExceptionForNullUsername() {
        // Act & Assert
        assertThatThrownBy(() -> new CreateUserRequest(null, 30))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Username cannot be null or empty");
    }
    
    @Test
    @DisplayName("Should throw IllegalArgumentException when username is blank")
    void shouldThrowIllegalArgumentExceptionForBlankUsername() {
        // Act & Assert
        assertThatThrownBy(() -> new CreateUserRequest("   ", 30))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Username cannot be null or empty");
    }
    
    @Test
    @DisplayName("Should throw IllegalArgumentException when username is empty")
    void shouldThrowIllegalArgumentExceptionForEmptyUsername() {
        // Act & Assert
        assertThatThrownBy(() -> new CreateUserRequest("", 30))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Username cannot be null or empty");
    }
    
    @Test
    @DisplayName("Should throw IllegalArgumentException when age is null")
    void shouldThrowIllegalArgumentExceptionForNullAge() {
        // Act & Assert
        assertThatThrownBy(() -> new CreateUserRequest("john_doe", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Age must be between 0 and 150");
    }
    
    @Test
    @DisplayName("Should throw IllegalArgumentException when age is negative")
    void shouldThrowIllegalArgumentExceptionForNegativeAge() {
        // Act & Assert
        assertThatThrownBy(() -> new CreateUserRequest("john_doe", -5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Age must be between 0 and 150");
    }
    
    @Test
    @DisplayName("Should throw IllegalArgumentException when age is greater than 150")
    void shouldThrowIllegalArgumentExceptionForAgeOver150() {
        // Act & Assert
        assertThatThrownBy(() -> new CreateUserRequest("john_doe", 151))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Age must be between 0 and 150");
    }
    
    @Test
    @DisplayName("Should accept age 0")
    void shouldAcceptAgeZero() {
        // Act
        CreateUserRequest request = new CreateUserRequest("john_doe", 0);
        
        // Assert
        assertThat(request.getAge()).isEqualTo(0);
    }
    
    @Test
    @DisplayName("Should accept age 150")
    void shouldAcceptAge150() {
        // Act
        CreateUserRequest request = new CreateUserRequest("john_doe", 150);
        
        // Assert
        assertThat(request.getAge()).isEqualTo(150);
    }
}

