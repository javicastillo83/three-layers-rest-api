package com.carusato.restapi.application.service;

import com.carusato.restapi.domain.User;
import com.carusato.restapi.domain.exception.UserAlreadyExistsException;
import com.carusato.restapi.domain.exception.UserNotFoundException;
import com.carusato.restapi.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for UserService.
 * 
 * Testing strategy follows FIRST principles:
 * - Fast: No external dependencies, only mocked
 * - Isolated: Each test is independent
 * - Repeatable: Consistent results
 * - Self-verifying: Clear pass/fail assertions
 * - Timely: Written alongside production code (TDD)
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("UserService Unit Tests")
class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    private UserService userService;
    
    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
    }
    
    // ============ Create User Tests ============
    
    @Test
    @DisplayName("Should create user successfully with valid data")
    void shouldCreateUserSuccessfully() {
        // Arrange
        String username = "john_doe";
        Integer age = 30;
        
        User expectedUser = new User.Builder()
                .id(1L)
                .username(username)
                .age(age)
                .active(true)
                .build();
        
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(expectedUser);
        
        // Act
        User createdUser = userService.createUser(username, age);
        
        // Assert
        assertThat(createdUser)
                .isNotNull()
                .extracting(User::getId, User::getUsername, User::getAge, User::isActive)
                .containsExactly(1L, username, age, true);
        
        verify(userRepository).findByUsername(username);
        verify(userRepository).save(any(User.class));
    }
    
    @Test
    @DisplayName("Should throw UserAlreadyExistsException when username already exists")
    void shouldThrowUserAlreadyExistsException() {
        // Arrange
        String username = "existing_user";
        Integer age = 30;
        
        User existingUser = new User.Builder()
                .id(1L)
                .username(username)
                .age(25)
                .active(true)
                .build();
        
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(existingUser));
        
        // Act & Assert
        assertThatThrownBy(() -> userService.createUser(username, age))
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessageContaining(username);
        
        verify(userRepository, never()).save(any(User.class));
    }
    
    @Test
    @DisplayName("Should throw IllegalArgumentException when username is blank")
    void shouldThrowIllegalArgumentExceptionForBlankUsername() {
        // Arrange
        String username = "   ";
        Integer age = 30;
        
        // Act & Assert
        assertThatThrownBy(() -> userService.createUser(username, age))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Username cannot be null or empty");
        
        verify(userRepository, never()).findByUsername(anyString());
        verify(userRepository, never()).save(any(User.class));
    }
    
    @Test
    @DisplayName("Should throw IllegalArgumentException when age is negative")
    void shouldThrowIllegalArgumentExceptionForNegativeAge() {
        // Arrange
        String username = "john_doe";
        Integer age = -5;
        
        // Act & Assert
        assertThatThrownBy(() -> userService.createUser(username, age))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Age must be between 0 and 150");
        
        verify(userRepository, never()).save(any(User.class));
    }
    
    @Test
    @DisplayName("Should throw IllegalArgumentException when age is greater than 150")
    void shouldThrowIllegalArgumentExceptionForAgeOver150() {
        // Arrange
        String username = "john_doe";
        Integer age = 151;
        
        // Act & Assert
        assertThatThrownBy(() -> userService.createUser(username, age))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Age must be between 0 and 150");
        
        verify(userRepository, never()).save(any(User.class));
    }
    
    // ============ Get User Tests ============
    
    @Test
    @DisplayName("Should retrieve user by ID successfully")
    void shouldGetUserByIdSuccessfully() {
        // Arrange
        Long userId = 1L;
        User expectedUser = new User.Builder()
                .id(userId)
                .username("john_doe")
                .age(30)
                .active(true)
                .build();
        
        when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));
        
        // Act
        Optional<User> foundUser = userService.getUserById(userId);
        
        // Assert
        assertThat(foundUser)
                .isPresent()
                .contains(expectedUser);
        
        verify(userRepository).findById(userId);
    }
    
    @Test
    @DisplayName("Should return empty Optional when user not found by ID")
    void shouldReturnEmptyOptionalWhenUserNotFoundById() {
        // Arrange
        Long userId = 999L;
        
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        
        // Act
        Optional<User> foundUser = userService.getUserById(userId);
        
        // Assert
        assertThat(foundUser).isEmpty();
        
        verify(userRepository).findById(userId);
    }
    
    @Test
    @DisplayName("Should retrieve user by username successfully")
    void shouldGetUserByUsernameSuccessfully() {
        // Arrange
        String username = "john_doe";
        User expectedUser = new User.Builder()
                .id(1L)
                .username(username)
                .age(30)
                .active(true)
                .build();
        
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(expectedUser));
        
        // Act
        Optional<User> foundUser = userService.getUserByUsername(username);
        
        // Assert
        assertThat(foundUser)
                .isPresent()
                .contains(expectedUser);
        
        verify(userRepository).findByUsername(username);
    }
    
    // ============ Update User Tests ============
    
    @Test
    @DisplayName("Should update user successfully")
    void shouldUpdateUserSuccessfully() {
        // Arrange
        Long userId = 1L;
        String newUsername = "jane_doe";
        Integer newAge = 35;
        
        User existingUser = new User.Builder()
                .id(userId)
                .username("john_doe")
                .age(30)
                .active(true)
                .build();
        
        User updatedUser = new User.Builder()
                .id(userId)
                .username(newUsername)
                .age(newAge)
                .active(true)
                .build();
        
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.findByUsername(newUsername)).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);
        
        // Act
        User result = userService.updateUser(userId, newUsername, newAge);
        
        // Assert
        assertThat(result)
                .isNotNull()
                .extracting(User::getId, User::getUsername, User::getAge)
                .containsExactly(userId, newUsername, newAge);
        
        verify(userRepository).findById(userId);
        verify(userRepository).findByUsername(newUsername);
        verify(userRepository).save(any(User.class));
    }
    
    @Test
    @DisplayName("Should throw UserNotFoundException when updating non-existing user")
    void shouldThrowUserNotFoundExceptionWhenUpdatingNonExistingUser() {
        // Arrange
        Long userId = 999L;
        
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThatThrownBy(() -> userService.updateUser(userId, "new_name", 30))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining(userId.toString());
        
        verify(userRepository, never()).save(any(User.class));
    }
    
    // ============ Deactivate User Tests ============
    
    @Test
    @DisplayName("Should deactivate user successfully")
    void shouldDeactivateUserSuccessfully() {
        // Arrange
        Long userId = 1L;
        User existingUser = new User.Builder()
                .id(userId)
                .username("john_doe")
                .age(30)
                .active(true)
                .build();
        
        User deactivatedUser = new User.Builder()
                .id(userId)
                .username("john_doe")
                .age(30)
                .active(false)
                .build();
        
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(deactivatedUser);
        
        // Act
        User result = userService.deactivateUser(userId);
        
        // Assert
        assertThat(result)
                .isNotNull()
                .extracting(User::isActive)
                .isEqualTo(false);
        
        verify(userRepository).findById(userId);
        verify(userRepository).save(any(User.class));
    }
    
    @Test
    @DisplayName("Should throw UserNotFoundException when deactivating non-existing user")
    void shouldThrowUserNotFoundExceptionWhenDeactivatingNonExistingUser() {
        // Arrange
        Long userId = 999L;
        
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThatThrownBy(() -> userService.deactivateUser(userId))
                .isInstanceOf(UserNotFoundException.class);
        
        verify(userRepository, never()).save(any(User.class));
    }
    
    // ============ Delete User Tests ============
    
    @Test
    @DisplayName("Should delete user successfully")
    void shouldDeleteUserSuccessfully() {
        // Arrange
        Long userId = 1L;
        
        when(userRepository.existsById(userId)).thenReturn(true);
        
        // Act
        userService.deleteUser(userId);
        
        // Assert
        verify(userRepository).existsById(userId);
        verify(userRepository).deleteById(userId);
    }
    
    @Test
    @DisplayName("Should throw UserNotFoundException when deleting non-existing user")
    void shouldThrowUserNotFoundExceptionWhenDeletingNonExistingUser() {
        // Arrange
        Long userId = 999L;
        
        when(userRepository.existsById(userId)).thenReturn(false);
        
        // Act & Assert
        assertThatThrownBy(() -> userService.deleteUser(userId))
                .isInstanceOf(UserNotFoundException.class);
        
        verify(userRepository, never()).deleteById(userId);
    }
}

