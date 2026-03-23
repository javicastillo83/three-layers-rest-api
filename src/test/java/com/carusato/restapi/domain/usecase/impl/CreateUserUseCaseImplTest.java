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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for CreateUserUseCaseImpl.
 * Tests the orchestration of user creation.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("CreateUserUseCaseImpl Unit Tests")
class CreateUserUseCaseImplTest {
    
    @Mock
    private UserService userService;
    
    private CreateUserUseCaseImpl createUserUseCase;
    
    @BeforeEach
    void setUp() {
        createUserUseCase = new CreateUserUseCaseImpl(userService);
    }
    
    @Test
    @DisplayName("Should execute create user use case successfully")
    void shouldExecuteCreateUserUseCaseSuccessfully() {
        // Arrange
        String username = "john_doe";
        Integer age = 30;
        User expectedUser = new User.Builder()
                .id(1L)
                .username(username)
                .age(age)
                .active(true)
                .build();
        
        when(userService.createUser(username, age)).thenReturn(expectedUser);
        
        // Act
        User result = createUserUseCase.execute(username, age);
        
        // Assert
        assertThat(result)
                .isNotNull()
                .extracting(User::getId, User::getUsername, User::getAge)
                .containsExactly(1L, username, age);
        
        verify(userService).createUser(username, age);
    }
    
    @Test
    @DisplayName("Should delegate to service on create user")
    void shouldDelegateToServiceOnCreateUser() {
        // Arrange
        String username = "jane_doe";
        Integer age = 25;
        User expectedUser = new User.Builder()
                .id(2L)
                .username(username)
                .age(age)
                .active(true)
                .build();
        
        when(userService.createUser(username, age)).thenReturn(expectedUser);
        
        // Act
        createUserUseCase.execute(username, age);
        
        // Assert - Verify that service was called
        verify(userService).createUser(username, age);
    }
}

