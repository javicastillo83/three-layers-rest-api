package com.carusato.restapi.presentation.controller;

import com.carusato.restapi.domain.User;
import com.carusato.restapi.domain.usecase.CreateUserUseCase;
import com.carusato.restapi.domain.usecase.DeactivateUserUseCase;
import com.carusato.restapi.domain.usecase.DeleteUserUseCase;
import com.carusato.restapi.domain.usecase.GetUserByIdUseCase;
import com.carusato.restapi.domain.usecase.UpdateUserUseCase;
import com.carusato.restapi.presentation.dto.CreateUserRequest;
import com.carusato.restapi.presentation.dto.UpdateUserRequest;
import com.carusato.restapi.presentation.dto.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * User REST Controller.
 * Handles HTTP requests for user CRUD operations.
 * Delegates business operations to Use Cases.
 */
@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private final CreateUserUseCase createUserUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final DeactivateUserUseCase deactivateUserUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    
    /**
     * Constructor injection of Use Cases.
     * @param createUserUseCase the create user use case
     * @param getUserByIdUseCase the get user by ID use case
     * @param updateUserUseCase the update user use case
     * @param deactivateUserUseCase the deactivate user use case
     * @param deleteUserUseCase the delete user use case
     */
    public UserController(
            CreateUserUseCase createUserUseCase,
            GetUserByIdUseCase getUserByIdUseCase,
            UpdateUserUseCase updateUserUseCase,
            DeactivateUserUseCase deactivateUserUseCase,
            DeleteUserUseCase deleteUserUseCase) {
        this.createUserUseCase = createUserUseCase;
        this.getUserByIdUseCase = getUserByIdUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.deactivateUserUseCase = deactivateUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
    }
    
    /**
     * Creates a new user.
     * HTTP Status: 201 Created
     * 
     * @param request the create user request DTO
     * @return the created user response with 201 status
     */
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserRequest request) {
        log.info("Creating user with username: {}", request.getUsername());
        
        User user = createUserUseCase.execute(request.getUsername(), request.getAge());
        UserResponse response = mapToResponse(user);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    /**
     * Retrieves a user by ID.
     * HTTP Status: 200 OK or 404 Not Found
     * 
     * @param id the user ID
     * @return the user response or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        log.debug("Fetching user with ID: {}", id);
        
        Optional<User> user = getUserByIdUseCase.execute(id);
        return user
                .map(u -> ResponseEntity.ok(mapToResponse(u)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    /**
     * Updates a user.
     * HTTP Status: 200 OK or 404 Not Found
     * 
     * @param id the user ID
     * @param request the update user request DTO
     * @return the updated user response or 404 if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @RequestBody UpdateUserRequest request) {
        
        log.info("Updating user with ID: {}", id);
        
        User user = updateUserUseCase.execute(id, request.getUsername(), request.getAge());
        UserResponse response = mapToResponse(user);
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Deactivates a user.
     * HTTP Status: 200 OK or 404 Not Found
     * 
     * @param id the user ID
     * @return the deactivated user response or 404 if not found
     */
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<UserResponse> deactivateUser(@PathVariable Long id) {
        log.info("Deactivating user with ID: {}", id);
        
        User user = deactivateUserUseCase.execute(id);
        UserResponse response = mapToResponse(user);
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * Deletes a user.
     * HTTP Status: 204 No Content or 404 Not Found
     * 
     * @param id the user ID
     * @return 204 No Content or 404 if not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.info("Deleting user with ID: {}", id);
        
        deleteUserUseCase.execute(id);
        
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Maps a domain User to a UserResponse DTO.
     * DRY principle: centralized mapping logic.
     * 
     * @param user the domain user
     * @return the user response DTO
     */
    private UserResponse mapToResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getAge(),
                user.isActive()
        );
    }
}

