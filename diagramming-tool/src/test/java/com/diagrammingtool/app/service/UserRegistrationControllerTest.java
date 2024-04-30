package com.diagrammingtool.app.service;
import com.diagrammingtool.app.dto.ResetPasswordRequest;
import com.diagrammingtool.app.OtpService.OtpService;
import com.diagrammingtool.app.controller.UserRegistrationController;
import com.diagrammingtool.app.model.UserRegistration;
import com.diagrammingtool.app.service.UserRegistrationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserRegistrationControllerTest {

    @Mock
    private UserRegistrationServiceImpl userService;

    @Mock
    private OtpService otpService;

    @InjectMocks
    private UserRegistrationController userRegistrationController;

    @Test
    public void testResetPasswordRequest() {
        String userEmail = "abinjames409@gmail.com";
       ResetPasswordRequest request = new ResetPasswordRequest();
        request.setUserEmail(userEmail);

        String otp = "123456"; // Mock OTP
        when(otpService.generateOtp()).thenReturn(otp);

        UserRegistration user = new UserRegistration();
        user.setUserEmail(userEmail);

        when(userService.getUserByEmail(userEmail)).thenReturn(user);
        ResponseEntity<?> responseEntity = userRegistrationController.resetPasswordRequest(request);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("OTP sent to " + userEmail + " for validation", responseEntity.getBody());

        verify(otpService, times(1)).generateOtp();
        verify(otpService, times(1)).sendOtpEmail(userEmail, otp);
    }

    @Test
    public void testResetPasswordVerify() {
        String userEmail = "abinjames409@gmail.com";
        String newPassword = "newPassword";
        String otp = "123456";

        ResetPasswordRequest request = new ResetPasswordRequest();
        request.setUserEmail(userEmail);
        request.setNewPassword(newPassword);
        request.setOtp(otp);

        UserRegistration user = new UserRegistration();
        user.setUserEmail(userEmail);
        user.setPassword("oldPassword");

        when(otpService.isValidOtp(userEmail, otp)).thenReturn(true);
        when(userService.getUserByEmail(userEmail)).thenReturn(user);

        ResponseEntity<?> responseEntity = userRegistrationController.resetPasswordVerify(request);

        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("Password reset successfully", responseEntity.getBody());

        verify(userService, times(1)).updateUser(user);
    }
    
    @Test
    public void testResetPasswordRequestUserNotFound() {
        String userEmail = "nonexistent@example.com";
        ResetPasswordRequest request = new ResetPasswordRequest();
        request.setUserEmail(userEmail);

        // Mock userService to return null, simulating user not found
        when(userService.getUserByEmail(userEmail)).thenReturn(null);

        ResponseEntity<?> responseEntity = userRegistrationController.resetPasswordRequest(request);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("User not found", responseEntity.getBody());
    }


    @Test
    public void testResetPasswordRequestInvalidOtp() {
        String userEmail = "user@example.com";
        ResetPasswordRequest request = new ResetPasswordRequest();
        request.setUserEmail(userEmail);
        request.setOtp("invalid_otp"); // Invalid OTP

      

        ResponseEntity<?> responseEntity = userRegistrationController.resetPasswordVerify(request);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Invalid OTP", responseEntity.getBody());
    }

    @Test
    public void testResetPasswordVerifyUserNotFound() {
        String userEmail = "nonexistent@example.com";
        ResetPasswordRequest request = new ResetPasswordRequest();
        request.setUserEmail(userEmail);

        // Mock userService to return null, simulating user not found
        when(userService.getUserByEmail(userEmail)).thenReturn(null);

        ResponseEntity<?> responseEntity = userRegistrationController.resetPasswordRequest(request);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("User not found", responseEntity.getBody());
    }
}
