package com.diagrammingtool.app.controller;

import com.diagrammingtool.app.model.UserLogin;
import com.diagrammingtool.app.service.UserLoginServiceImpl;
import com.diagrammingtool.app.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserLoginControllerTest {

    @Mock
    private UserLoginServiceImpl userLoginService;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserLoginController userLoginController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUserLogin_Success() {
        // Mock data
        UserLogin userLogin = new UserLogin();
        userLogin.setUserEmail("test@example.com");
        userLogin.setPassword("password");

        String jwtToken = "mockJwtToken";
        doNothing().when(userLoginService).loginUser("test@example.com", "password");
        when(jwtUtil.generateToken("test@example.com")).thenReturn(jwtToken);
        ResponseEntity<?> response = userLoginController.UserRegistration(userLogin);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(jwtToken, response.getBody());
    }


    @Test
    public void testUserLogin_UserNotFound() {
        UserLogin userLogin = new UserLogin();
        userLogin.setUserEmail("test@example.com");
        userLogin.setPassword("password");
        doThrow(new IllegalArgumentException("user not found")).when(userLoginService).loginUser("test@example.com", "password");
        ResponseEntity<?> response = userLoginController.UserRegistration(userLogin);

        // Verify
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("user not found", response.getBody());
    }

    @Test
    public void testUserLogin_InvalidPassword() {
        UserLogin userLogin = new UserLogin();
        userLogin.setUserEmail("test@example.com");
        userLogin.setPassword("password");
        doThrow(new IllegalArgumentException("invalid password")).when(userLoginService).loginUser("test@example.com", "password");
        ResponseEntity<?> response = userLoginController.UserRegistration(userLogin);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("invalid password", response.getBody());
    }

    @Test
    public void testChangePassword_Success() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("userEmail", "test@example.com");
        requestBody.put("currentPassword", "currentPassword");
        requestBody.put("newPassword", "newPassword");
        requestBody.put("confirmPassword", "newPassword");
        requestBody.put("jwtToken", "mockJwtToken");

        // Mock service method
        when(userLoginService.verifyUserPassword("test@example.com", "currentPassword")).thenReturn(true);
        when(jwtUtil.getUsernameFromToken("mockJwtToken")).thenReturn("test@example.com");

        // Call the controller method
        ResponseEntity<?> response = userLoginController.changePassword(requestBody);

        // Verify
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Password changed successfully", response.getBody());
        verify(userLoginService).changeUserPassword("test@example.com", "newPassword");
    }

    @Test
    public void testChangePassword_IncorrectCurrentPassword() {
        // Mock data
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("userEmail", "test@example.com");
        requestBody.put("currentPassword", "currentPassword");
        requestBody.put("newPassword", "newPassword");
        requestBody.put("confirmPassword", "newPassword");
        requestBody.put("jwtToken", "mockJwtToken");

        when(userLoginService.verifyUserPassword("test@example.com", "currentPassword")).thenReturn(false);
        ResponseEntity<?> response = userLoginController.changePassword(requestBody);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Incorrect current password", response.getBody());
    }

    @Test
    public void testChangePassword_PasswordMismatch() {
        // Mock data
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("userEmail", "test@example.com");
        requestBody.put("currentPassword", "currentPassword");
        requestBody.put("newPassword", "newPassword");
        requestBody.put("confirmPassword", "differentPassword");
        requestBody.put("jwtToken", "mockJwtToken");

        ResponseEntity<?> response = userLoginController.changePassword(requestBody);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Incorrect current password", response.getBody());
    }

    @Test
    public void testChangePassword_UnauthorizedEmail() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("userEmail", "test@example.com");
        requestBody.put("currentPassword", "currentPassword");
        requestBody.put("newPassword", "newPassword");
        requestBody.put("confirmPassword", "newPassword");
        requestBody.put("jwtToken", "mockJwtToken");
        when(userLoginService.verifyUserPassword("test@example.com", "currentPassword")).thenReturn(true);
        when(jwtUtil.getUsernameFromToken("mockJwtToken")).thenReturn("different@example.com");
        ResponseEntity<?> response = userLoginController.changePassword(requestBody);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Unauthorized email", response.getBody());
    }
}
