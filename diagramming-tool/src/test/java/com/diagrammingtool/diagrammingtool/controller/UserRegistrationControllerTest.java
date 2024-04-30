package com.diagrammingtool.diagrammingtool.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.diagrammingtool.diagrammingtool.dto.UserLoginDto;
import com.diagrammingtool.diagrammingtool.model.UserRegistration;
import com.diagrammingtool.diagrammingtool.service.JwtService;
import com.diagrammingtool.diagrammingtool.service.UserRegistrationService;

public class UserRegistrationControllerTest {

    private UserRegistrationService userRegistrationService;
    private JwtService jwtService;
    private UserRegistrationController userRegistrationController;

    @BeforeEach
    public void setUp() {
        userRegistrationService = mock(UserRegistrationService.class);
        jwtService = mock(JwtService.class);
        userRegistrationController = new UserRegistrationController(jwtService, userRegistrationService);
    }

    @Test
    public void testAuthenticate_Success() {
        // Mock userLoginDto
        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setUserEmail("test@example.com");
        userLoginDto.setPassword("password123");

        // Mock authenticated user
        UserRegistration authenticatedUser = new UserRegistration();
        authenticatedUser.setUserEmail("test@example.com");
        authenticatedUser.setPassword("password123");

        // Mock JWT token
        String jwtToken = "mockJwtToken";

        // Mock service method calls
        when(userRegistrationService.authenticate(userLoginDto)).thenReturn(authenticatedUser);
        when(jwtService.generateToken(authenticatedUser)).thenReturn(jwtToken);

        // Call the method under test
        ResponseEntity<?> response = userRegistrationController.authenticate(userLoginDto);

        // Verify the response status and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(jwtToken, response.getBody());
    }

    @Test
    public void testAuthenticate_UserNotFound() {
        // Mock userLoginDto
        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setUserEmail("nonexistent@example.com");
        userLoginDto.setPassword("password123");

        // Mock service method call
        when(userRegistrationService.authenticate(userLoginDto)).thenThrow(new IllegalArgumentException("user not found"));

        // Call the method under test
        ResponseEntity<?> response = userRegistrationController.authenticate(userLoginDto);

        // Verify the response status and body
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("user not found", response.getBody());
    }

    @Test
    public void testAuthenticate_InvalidPassword() {
        // Mock userLoginDto
        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setUserEmail("test@example.com");
        userLoginDto.setPassword("wrongpassword");

        // Mock service method call
        when(userRegistrationService.authenticate(userLoginDto)).thenThrow(new IllegalArgumentException("invalid password"));

        // Call the method under test
        ResponseEntity<?> response = userRegistrationController.authenticate(userLoginDto);

        // Verify the response status and body
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("invalid password", response.getBody());
    }
    
    @Test
    void register_Success() {
        // Mock dependencies
        UserRegistrationService userService = mock(UserRegistrationService.class);
        UserRegistrationController userController = new UserRegistrationController(null, userService);

        // Create a mock input
        UserRegistration input = new UserRegistration();
        input.setUserEmail("test@example.com");
        input.setPassword("password");

        // Mock the behavior of userService.createNewUser to return the created user
        UserRegistration createdUser = new UserRegistration();
        createdUser.setUserId(1L);
        createdUser.setUserEmail("test@example.com");
        when(userService.createNewUser(input)).thenReturn(createdUser);

        // Call the method being tested
        ResponseEntity<?> responseEntity = userController.register(input);

        // Assert the response status
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        // Assert the response body
        assertEquals(createdUser, responseEntity.getBody());
    }

    @Test
    void register_EmailAlreadyExists() {
        // Mock dependencies
        UserRegistrationService userService = mock(UserRegistrationService.class);
        UserRegistrationController userController = new UserRegistrationController(null, userService);

        // Create a mock input
        UserRegistration input = new UserRegistration();
        input.setUserEmail("test@example.com");
        input.setPassword("password");

        // Mock the behavior of userService.createNewUser to throw IllegalArgumentException
        when(userService.createNewUser(input)).thenThrow(new IllegalArgumentException("Email already exists"));

        // Call the method being tested
        ResponseEntity<?> responseEntity = userController.register(input);

        // Assert the response status
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Email already exists", responseEntity.getBody());
    }
    
    
}


