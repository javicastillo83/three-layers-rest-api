package com.carusato.restapi.domain.usecase.impl;

import com.carusato.restapi.application.service.UserService;
import com.carusato.restapi.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for GetUserByIdUseCaseImpl.
 * Tests the orchestration of user retrieval by ID.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("GetUserByIdUseCaseImpl Unit Tests")
class GetUserByIdUseCaseImplTest {
    
    @Mock
    private UserService userService;
    
    private GetUserByIdUseCaseImpl getUserByIdUseCase;
    
    @BeforeEach
    void setUp() {
        getUserByIdUseCase = new GetUserByIdUseCaseImpl(userService);
    }
    
    @Test
    @DisplayName("Should execute get user by ID use case successfully")
    void shouldExecuteGetUserByIdUseCaseSuccessfully() {
        // Arrange
        Long userId = 1L;
        User expectedUser = new User.Builder()
                .id(userId)
                .username("john_doe")
                .age(30)
                .active(true)
                .build();
        
        when(userService.getUserById(userId)).thenReturn(Optional.of(expectedUser));
        
        // Act
        Optional<User> result = getUserByIdUseCase.execute(userId);
        
        // Assert
        assertThat(result)
                .isPresent()
                .contains(expectedUser);
        
        verify(userService).getUserById(userId);
    }
    
    @Test
    @DisplayName("Should return empty Optional when user not found")
    void shouldReturnEmptyOptionalWhenUserNotFound() {
        // Arrange
        Long userId = 999L;
        
        when(userService.getUserById(userId)).thenReturn(Optional.empty());
        
        // Act
        Optional<User> result = getUserByIdUseCase.execute(userId);
        
        // Assert
        assertThat(result).isEmpty();
        
        verify(userService).getUserById(userId);
    }
}

