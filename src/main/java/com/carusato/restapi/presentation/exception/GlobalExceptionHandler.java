package com.carusato.restapi.presentation.exception;

import com.carusato.restapi.domain.exception.UserAlreadyExistsException;
import com.carusato.restapi.domain.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Global exception handler for REST API.
 * Centralizes exception handling across all controllers.
 * Returns consistent error response format.
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    
    /**
     * Handles UserNotFoundException.
     * HTTP Status: 404 Not Found
     * 
     * @param exception the user not found exception
     * @return error response with 404 status
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFoundException(
            UserNotFoundException exception) {
        
        log.warn("User not found: {}", exception.getMessage());
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND));
    }
    
    /**
     * Handles UserAlreadyExistsException.
     * HTTP Status: 409 Conflict
     * 
     * @param exception the user already exists exception
     * @return error response with 409 status
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleUserAlreadyExistsException(
            UserAlreadyExistsException exception) {
        
        log.warn("User already exists: {}", exception.getMessage());
        
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(buildErrorResponse(exception.getMessage(), HttpStatus.CONFLICT));
    }
    
    /**
     * Handles IllegalArgumentException.
     * HTTP Status: 400 Bad Request
     * 
     * @param exception the illegal argument exception
     * @return error response with 400 status
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(
            IllegalArgumentException exception) {
        
        log.warn("Invalid argument: {}", exception.getMessage());
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(buildErrorResponse(exception.getMessage(), HttpStatus.BAD_REQUEST));
    }
    
    /**
     * Handles generic exceptions.
     * HTTP Status: 500 Internal Server Error
     * 
     * @param exception the exception
     * @return error response with 500 status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception exception) {
        log.error("Unexpected error occurred", exception);
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildErrorResponse("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR));
    }
    
    /**
     * Builds a consistent error response structure.
     * DRY principle: centralized error response building.
     * 
     * @param message the error message
     * @param status the HTTP status
     * @return the error response map
     */
    private Map<String, Object> buildErrorResponse(String message, HttpStatus status) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("error", status.getReasonPhrase());
        response.put("message", message);
        response.put("status", status.value());
        response.put("timestamp", LocalDateTime.now());
        return response;
    }
}

