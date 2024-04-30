package com.diagrammingtool.diagrammingtool.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import com.diagrammingtool.diagrammingtool.model.CanvasImage;
import com.diagrammingtool.diagrammingtool.model.UserRegistration;
import com.diagrammingtool.diagrammingtool.service.CanvasImageService;
import com.diagrammingtool.diagrammingtool.service.UserRegistrationService;

class UserControllerTest {

    @Mock
    private UserRegistrationService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUpdatePassword_Success() {
        // Prepare test data
        String userEmail = "testuser@example.com";
        String currentPassword = "oldPassword";
        String newPassword = "newPassword";

        // Mock the request body
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("userEmail", userEmail);
        requestBody.put("currentPassword", currentPassword);
        requestBody.put("newPassword", newPassword);

        // Mock the service method
        when(userService.verifyUserPassword(userEmail, currentPassword)).thenReturn(true);

        // Call the controller method
        ResponseEntity<?> response = userController.updatePassword(requestBody);

        // Verify the service method invocation
        verify(userService).verifyUserPassword(userEmail, currentPassword);
        verify(userService).changeUserPassword(userEmail, newPassword);

        // Assert the response status
        assert response.getStatusCode() == HttpStatus.OK;
    }
    
    @Test
    void testUpdatePassword_IncorrectCurrentPassword() {
        // Prepare test data
        String userEmail = "testuser@example.com";
        String currentPassword = "wrongPassword";
        String newPassword = "newPassword";

        // Mock the request body
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("userEmail", userEmail);
        requestBody.put("currentPassword", currentPassword);
        requestBody.put("newPassword", newPassword);

        // Mock the service method to return false for incorrect password
        when(userService.verifyUserPassword(userEmail, currentPassword)).thenReturn(false);

        // Call the controller method
        ResponseEntity<?> response = userController.updatePassword(requestBody);

        // Verify the service method invocation
        verify(userService).verifyUserPassword(userEmail, currentPassword);
        verify(userService, never()).changeUserPassword(anyString(), anyString());

        // Assert the response status
        assert response.getStatusCode() == HttpStatus.UNAUTHORIZED;
    }
    
    
   
    
   
}
