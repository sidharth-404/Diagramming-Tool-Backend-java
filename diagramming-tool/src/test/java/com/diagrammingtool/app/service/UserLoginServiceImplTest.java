package com.diagrammingtool.app.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.diagrammingtool.app.model.UserRegistration;
import com.diagrammingtool.app.repository.UserRegistrationRepository;


@SpringBootTest
public class UserLoginServiceImplTest {

    @Mock
    private UserRegistrationRepository userDetails;

    @Mock
    private PasswordEncoder passwordEncrypt;

    @InjectMocks
    private UserLoginServiceImpl userLoginService;

    @Test
    public void testLoginUser_Success() {
        UserRegistration user = new UserRegistration();
        user.setUserEmail("test@example.com");
        user.setPassword("password");

        when(userDetails.findByUserEmail(any(String.class))).thenReturn(user);
        when(passwordEncrypt.matches(any(String.class), any(String.class))).thenReturn(true);

        userLoginService.loginUser("test@example.com", "password");

        verify(userDetails, times(1)).findByUserEmail("test@example.com");
    }

    @Test
    public void testLoginUser_UserNotFound() {
        when(userDetails.findByUserEmail(any(String.class))).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> {
            userLoginService.loginUser("test@example.com", "password");
        });
    }

    @Test
    public void testLoginUser_InvalidPassword() {
        UserRegistration user = new UserRegistration();
        user.setUserEmail("test@example.com");
        user.setPassword("password");

        when(userDetails.findByUserEmail(any(String.class))).thenReturn(user);
        when(passwordEncrypt.matches(any(String.class), any(String.class))).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> {
            userLoginService.loginUser("test@example.com", "invalid_password");
        });
    }
    
    @Test
    public void testVerifyUserPassword_ValidPassword() {
        // Arrange
        String userEmail = "test@example.com";
        String password = "password123";
        String encodedPassword = "encodedPassword123";
        UserRegistration user = new UserRegistration();
        user.setUserEmail(userEmail);
        user.setPassword(encodedPassword);
        when(userDetails.findByUserEmail(userEmail)).thenReturn(user);
        when(passwordEncrypt.matches(password, encodedPassword)).thenReturn(true);

        // Act
        boolean result = userLoginService.verifyUserPassword(userEmail, password);

        assertTrue(result);
        verify(userDetails, times(1)).findByUserEmail(userEmail);
        verify(passwordEncrypt, times(1)).matches(password, encodedPassword);
    }
    
    @Test
    public void testVerifyUserPassword_InvalidPassword() {
        // Arrange
        String userEmail = "test@example.com";
        String password = "password123";
        String encodedPassword = "encodedPassword123";
        UserRegistration user = new UserRegistration();
        user.setUserEmail(userEmail);
        user.setPassword(encodedPassword);
        when(userDetails.findByUserEmail(userEmail)).thenReturn(user);
        when(passwordEncrypt.matches(password, encodedPassword)).thenReturn(false);

        boolean result = userLoginService.verifyUserPassword(userEmail, password);

        assertFalse(result);
        verify(userDetails, times(1)).findByUserEmail(userEmail);
        verify(passwordEncrypt, times(1)).matches(password, encodedPassword);
    }
    
    @Test
    public void testChangeUserPassword() {
    
        String userEmail = "test@example.com";
        String newPassword = "newPassword123";
        String encodedPassword = "encodedPassword123";
        UserRegistration user = new UserRegistration();
        user.setUserEmail(userEmail);
        user.setPassword(encodedPassword);
        when(userDetails.findByUserEmail(userEmail)).thenReturn(user);
        when(passwordEncrypt.encode(newPassword)).thenReturn("newEncodedPassword123");

     
        userLoginService.changeUserPassword(userEmail, newPassword);

        verify(userDetails, times(1)).findByUserEmail(userEmail);
        verify(passwordEncrypt, times(1)).encode(newPassword);
        assertEquals("newEncodedPassword123", user.getPassword());
        verify(userDetails, times(1)).save(user);
    }
    

    @Test
    public void testChangeUserPassword_UserNotFound() {
        
        String userEmail = "nonexistent@example.com";
        String newPassword = "newPassword123";
        when(userDetails.findByUserEmail(userEmail)).thenReturn(null);

       
        assertThrows(IllegalArgumentException.class, () -> userLoginService.changeUserPassword(userEmail, newPassword));
        verify(userDetails, times(1)).findByUserEmail(userEmail);
        verify(passwordEncrypt, never()).encode(newPassword);
        verify(userDetails, never()).save(any());
    }
    
    @Test
    public void testVerifyUserPassword_UserNotFound() {
        // Arrange
        String userEmail = "nonexistent@example.com";
        String newPassword = "password123";
        when(userDetails.findByUserEmail(userEmail)).thenReturn(null);

        
        assertThrows(IllegalArgumentException.class, () -> {
            userLoginService.verifyUserPassword(userEmail, newPassword);
            verify(userDetails, times(1)).findByUserEmail(userEmail);
            verify(passwordEncrypt, never()).encode(newPassword);
            verify(userDetails, never()).save(any());
        });
    }
}
