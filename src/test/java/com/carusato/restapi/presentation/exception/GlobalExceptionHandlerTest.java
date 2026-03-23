package com.carusato.restapi.presentation.exception;

import com.carusato.restapi.domain.exception.UserAlreadyExistsException;
import com.carusato.restapi.domain.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for GlobalExceptionHandler.
 * Tests exception handling and HTTP response mapping.
 */
@DisplayName("GlobalExceptionHandler Unit Tests")
class GlobalExceptionHandlerTest {
    
    private GlobalExceptionHandler exceptionHandler;
    
    @BeforeEach
    void setUp() {
        exceptionHandler = new GlobalExceptionHandler();
    }
    
    // ============ UserNotFoundException Tests ============
    
    @Test
    @DisplayName("Should return 404 Not Found for UserNotFoundException")
    void shouldReturn404ForUserNotFoundException() {
        // Arrange
        UserNotFoundException exception = new UserNotFoundException(1L);
        
        // Act
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleUserNotFoundException(exception);
        
        // Assert
        assertThat(response)
                .isNotNull()
                .extracting(ResponseEntity::getStatusCode)
                .isEqualTo(HttpStatus.NOT_FOUND);
        
        assertThat(response.getBody())
                .isNotNull()
                .containsEntry("error", HttpStatus.NOT_FOUND.getReasonPhrase())
                .containsEntry("status", HttpStatus.NOT_FOUND.value())
                .containsKey("message")
                .containsKey("timestamp");
    }
    
    @Test
    @DisplayName("Should include error message in response for UserNotFoundException")
    void shouldIncludeErrorMessageForUserNotFoundException() {
        // Arrange
        UserNotFoundException exception = new UserNotFoundException(999L);
        
        // Act
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleUserNotFoundException(exception);
        
        // Assert
        assertThat(response.getBody())
                .containsEntry("message", "User not found with ID: 999")
                .containsEntry("status", 404);
    }
    
    // ============ UserAlreadyExistsException Tests ============
    
    @Test
    @DisplayName("Should return 409 Conflict for UserAlreadyExistsException")
    void shouldReturn409ForUserAlreadyExistsException() {
        // Arrange
        UserAlreadyExistsException exception = new UserAlreadyExistsException("john_doe");
        
        // Act
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleUserAlreadyExistsException(exception);
        
        // Assert
        assertThat(response)
                .isNotNull()
                .extracting(ResponseEntity::getStatusCode)
                .isEqualTo(HttpStatus.CONFLICT);
        
        assertThat(response.getBody())
                .isNotNull()
                .containsEntry("error", HttpStatus.CONFLICT.getReasonPhrase())
                .containsEntry("status", HttpStatus.CONFLICT.value());
    }
    
    @Test
    @DisplayName("Should include error message in response for UserAlreadyExistsException")
    void shouldIncludeErrorMessageForUserAlreadyExistsException() {
        // Arrange
        UserAlreadyExistsException exception = new UserAlreadyExistsException("john_doe");
        
        // Act
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleUserAlreadyExistsException(exception);
        
        // Assert
        assertThat(response.getBody())
                .containsEntry("message", "User already exists with username: john_doe")
                .containsEntry("status", 409);
    }
    
    // ============ IllegalArgumentException Tests ============
    
    @Test
    @DisplayName("Should return 400 Bad Request for IllegalArgumentException")
    void shouldReturn400ForIllegalArgumentException() {
        // Arrange
        IllegalArgumentException exception = new IllegalArgumentException("Invalid username");
        
        // Act
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleIllegalArgumentException(exception);
        
        // Assert
        assertThat(response)
                .isNotNull()
                .extracting(ResponseEntity::getStatusCode)
                .isEqualTo(HttpStatus.BAD_REQUEST);
        
        assertThat(response.getBody())
                .isNotNull()
                .containsEntry("error", HttpStatus.BAD_REQUEST.getReasonPhrase())
                .containsEntry("status", HttpStatus.BAD_REQUEST.value());
    }
    
    @Test
    @DisplayName("Should include error message in response for IllegalArgumentException")
    void shouldIncludeErrorMessageForIllegalArgumentException() {
        // Arrange
        IllegalArgumentException exception = new IllegalArgumentException("Age must be between 0 and 150");
        
        // Act
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleIllegalArgumentException(exception);
        
        // Assert
        assertThat(response.getBody())
                .containsEntry("message", "Age must be between 0 and 150")
                .containsEntry("status", 400);
    }
    
    // ============ Generic Exception Tests ============
    
    @Test
    @DisplayName("Should return 500 Internal Server Error for generic Exception")
    void shouldReturn500ForGenericException() {
        // Arrange
        Exception exception = new RuntimeException("Unexpected error");
        
        // Act
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleGenericException(exception);
        
        // Assert
        assertThat(response)
                .isNotNull()
                .extracting(ResponseEntity::getStatusCode)
                .isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        
        assertThat(response.getBody())
                .isNotNull()
                .containsEntry("error", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .containsEntry("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
    
    @Test
    @DisplayName("Should return generic message for unexpected errors")
    void shouldReturnGenericMessageForUnexpectedErrors() {
        // Arrange
        Exception exception = new RuntimeException("Some unexpected error");
        
        // Act
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleGenericException(exception);
        
        // Assert
        assertThat(response.getBody())
                .containsEntry("message", "Internal server error")
                .containsEntry("status", 500);
    }
    
    // ============ Response Structure Tests ============
    
    @Test
    @DisplayName("Should include timestamp in all error responses")
    void shouldIncludeTimestampInAllResponses() {
        // Arrange
        UserNotFoundException exception = new UserNotFoundException(1L);
        
        // Act
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleUserNotFoundException(exception);
        
        // Assert
        assertThat(response.getBody())
                .containsKey("timestamp");
        
        Object timestamp = response.getBody().get("timestamp");
        assertThat(timestamp).isInstanceOf(LocalDateTime.class);
    }
    
    @Test
    @DisplayName("Should return all required fields in error response")
    void shouldReturnAllRequiredFieldsInErrorResponse() {
        // Arrange
        UserAlreadyExistsException exception = new UserAlreadyExistsException("test_user");
        
        // Act
        ResponseEntity<Map<String, Object>> response = exceptionHandler.handleUserAlreadyExistsException(exception);
        
        // Assert
        assertThat(response.getBody())
                .containsKeys("error", "message", "status", "timestamp")
                .hasSize(4);
    }
}

