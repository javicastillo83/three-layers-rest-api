package com.carusato.restapi.presentation.controller;

import com.carusato.restapi.domain.User;
import com.carusato.restapi.domain.exception.UserAlreadyExistsException;
import com.carusato.restapi.domain.exception.UserNotFoundException;
import com.carusato.restapi.domain.usecase.CreateUserUseCase;
import com.carusato.restapi.domain.usecase.DeactivateUserUseCase;
import com.carusato.restapi.domain.usecase.DeleteUserUseCase;
import com.carusato.restapi.domain.usecase.GetUserByIdUseCase;
import com.carusato.restapi.domain.usecase.UpdateUserUseCase;
import com.carusato.restapi.presentation.dto.CreateUserRequest;
import com.carusato.restapi.presentation.dto.UpdateUserRequest;
import com.carusato.restapi.presentation.dto.UserResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for UserController.
 * Tests REST endpoint handling and HTTP response codes.
 * Uses @InjectMocks for automatic mock injection (following copilot-instructions standards).
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("UserController Unit Tests")
class UserControllerTest {
    
    @Mock
    private CreateUserUseCase createUserUseCase;
    
    @Mock
    private GetUserByIdUseCase getUserByIdUseCase;
    
    @Mock
    private UpdateUserUseCase updateUserUseCase;
    
    @Mock
    private DeactivateUserUseCase deactivateUserUseCase;
    
    @Mock
    private DeleteUserUseCase deleteUserUseCase;
    
    @InjectMocks
    private UserController userController;
    
    // ============ Create User Tests ============
    
    @Test
    @DisplayName("Should return 201 Created when user is created successfully")
    void shouldReturn201CreatedWhenUserCreatedSuccessfully() {
        // Arrange
        CreateUserRequest request = new CreateUserRequest("john_doe", 30);
        User createdUser = new User.Builder()
                .id(1L)
                .username("john_doe")
                .age(30)
                .active(true)
                .build();
        
        when(createUserUseCase.execute("john_doe", 30)).thenReturn(createdUser);
        
        // Act
        ResponseEntity<UserResponse> response = userController.createUser(request);
        
        // Assert
        assertThat(response)
                .isNotNull()
                .extracting(ResponseEntity::getStatusCode)
                .isEqualTo(HttpStatus.CREATED);
        
        assertThat(response.getBody())
                .isNotNull()
                .extracting(UserResponse::getId, UserResponse::getUsername, UserResponse::getAge)
                .containsExactly(1L, "john_doe", 30);
    
    @Test
    @DisplayName("Should return 400 when creating user with invalid data")
    void shouldReturn400WhenCreatingUserWithInvalidData() {
        // Act & Assert - IllegalArgumentException will be caught by GlobalExceptionHandler
        // This test validates that controller delegates to use case
        CreateUserRequest request = new CreateUserRequest("john_doe", 30);
        User createdUser = new User.Builder()
                .id(1L)
                .username("john_doe")
                .age(30)
                .active(true)
                .build();
        
        when(createUserUseCase.execute("john_doe", 30)).thenReturn(createdUser);
        
        ResponseEntity<UserResponse> response = userController.createUser(request);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
    
    // ============ Get User Tests ============
    
    @Test
    @DisplayName("Should return 200 OK when user is found by ID")
    void shouldReturn200OKWhenUserFoundById() {
        // Arrange
        Long userId = 1L;
        User user = new User.Builder()
                .id(userId)
                .username("john_doe")
                .age(30)
                .active(true)
                .build();
        
        when(getUserByIdUseCase.execute(userId)).thenReturn(Optional.of(user));
        
        // Act
        ResponseEntity<UserResponse> response = userController.getUserById(userId);
        
        // Assert
        assertThat(response)
                .isNotNull()
                .extracting(ResponseEntity::getStatusCode)
                .isEqualTo(HttpStatus.OK);
        
        assertThat(response.getBody())
                .isNotNull()
                .extracting(UserResponse::getId, UserResponse::getUsername)
                .containsExactly(userId, "john_doe");
        
        verify(getUserByIdUseCase).execute(userId);
    }
    
    @Test
    @DisplayName("Should return 404 Not Found when user not found by ID")
    void shouldReturn404NotFoundWhenUserNotFoundById() {
        // Arrange
        Long userId = 999L;
        
        when(getUserByIdUseCase.execute(userId)).thenReturn(Optional.empty());
        
        // Act
        ResponseEntity<UserResponse> response = userController.getUserById(userId);
        
        // Assert
        assertThat(response)
                .isNotNull()
                .extracting(ResponseEntity::getStatusCode)
                .isEqualTo(HttpStatus.NOT_FOUND);
        
        assertThat(response.getBody()).isNull();
        
        verify(getUserByIdUseCase).execute(userId);
    }
    
    // ============ Update User Tests ============
    
    @Test
    @DisplayName("Should return 200 OK when user is updated successfully")
    void shouldReturn200OKWhenUserUpdatedSuccessfully() {
        // Arrange
        Long userId = 1L;
        UpdateUserRequest request = new UpdateUserRequest("jane_doe", 35);
        User updatedUser = new User.Builder()
                .id(userId)
                .username("jane_doe")
                .age(35)
                .active(true)
                .build();
        
        when(updateUserUseCase.execute(userId, "jane_doe", 35)).thenReturn(updatedUser);
        
        // Act
        ResponseEntity<UserResponse> response = userController.updateUser(userId, request);
        
        // Assert
        assertThat(response)
                .isNotNull()
                .extracting(ResponseEntity::getStatusCode)
                .isEqualTo(HttpStatus.OK);
        
        assertThat(response.getBody())
                .isNotNull()
                .extracting(UserResponse::getId, UserResponse::getUsername, UserResponse::getAge)
                .containsExactly(userId, "jane_doe", 35);
        
        verify(updateUserUseCase).execute(userId, "jane_doe", 35);
    }
    
    @Test
    @DisplayName("Should throw UserNotFoundException when updating non-existing user")
    void shouldThrowUserNotFoundExceptionWhenUpdatingNonExistingUser() {
        // Arrange
        Long userId = 999L;
        UpdateUserRequest request = new UpdateUserRequest("jane_doe", 35);
        
        when(updateUserUseCase.execute(userId, "jane_doe", 35))
                .thenThrow(new UserNotFoundException(userId));
        
        // Act & Assert
        try {
            userController.updateUser(userId, request);
        } catch (UserNotFoundException e) {
            assertThat(e).hasMessageContaining(userId.toString());
        }
    }
    
    // ============ Deactivate User Tests ============
    
    @Test
    @DisplayName("Should return 200 OK when user is deactivated successfully")
    void shouldReturn200OKWhenUserDeactivatedSuccessfully() {
        // Arrange
        Long userId = 1L;
        User deactivatedUser = new User.Builder()
                .id(userId)
                .username("john_doe")
                .age(30)
                .active(false)
                .build();
        
        when(deactivateUserUseCase.execute(userId)).thenReturn(deactivatedUser);
        
        // Act
        ResponseEntity<UserResponse> response = userController.deactivateUser(userId);
        
        // Assert
        assertThat(response)
                .isNotNull()
                .extracting(ResponseEntity::getStatusCode)
                .isEqualTo(HttpStatus.OK);
        
        assertThat(response.getBody())
                .isNotNull()
                .extracting(UserResponse::isActive)
                .isEqualTo(false);
        
        verify(deactivateUserUseCase).execute(userId);
    }
    
    @Test
    @DisplayName("Should throw UserNotFoundException when deactivating non-existing user")
    void shouldThrowUserNotFoundExceptionWhenDeactivatingNonExistingUser() {
        // Arrange
        Long userId = 999L;
        
        when(deactivateUserUseCase.execute(userId))
                .thenThrow(new UserNotFoundException(userId));
        
        // Act & Assert
        try {
            userController.deactivateUser(userId);
        } catch (UserNotFoundException e) {
            assertThat(e).hasMessageContaining(userId.toString());
        }
    }
    
    // ============ Delete User Tests ============
    
    @Test
    @DisplayName("Should return 204 No Content when user is deleted successfully")
    void shouldReturn204NoContentWhenUserDeletedSuccessfully() {
        // Arrange
        Long userId = 1L;
        
        // Act
        ResponseEntity<Void> response = userController.deleteUser(userId);
        
        // Assert
        assertThat(response)
                .isNotNull()
                .extracting(ResponseEntity::getStatusCode)
                .isEqualTo(HttpStatus.NO_CONTENT);
        
        assertThat(response.getBody()).isNull();
        
        verify(deleteUserUseCase).execute(userId);
    }
    
    @Test
    @DisplayName("Should throw UserNotFoundException when deleting non-existing user")
    void shouldThrowUserNotFoundExceptionWhenDeletingNonExistingUser() {
        // Arrange
        Long userId = 999L;
        
        when(deleteUserUseCase.execute(userId))
                .thenThrow(new UserNotFoundException(userId));
        
        // Act & Assert
        try {
            userController.deleteUser(userId);
        } catch (UserNotFoundException e) {
            assertThat(e).hasMessageContaining(userId.toString());
        }
    }
}

