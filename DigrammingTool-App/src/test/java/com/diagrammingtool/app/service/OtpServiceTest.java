package com.diagrammingtool.app.service;

import com.diagrammingtool.app.OtpService.OtpService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class OtpServiceTest {

    @Mock
    private JavaMailSender emailSender;

    @InjectMocks
    private OtpService otpService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGenerateOtp() {
        String otp = otpService.generateOtp();

        assertNotNull(otp);
        assertEquals(6, otp.length());
        assertTrue(otp.matches("\\d{6}")); // Check if OTP is 6 digits numeric
    }

    @Test
    public void testSendOtpEmail() {
        String userEmail = "test@example.com";
        String otp = "123456";

        // Mock email sending
        doNothing().when(emailSender).send(any(SimpleMailMessage.class));

        otpService.sendOtpEmail(userEmail, otp);

        // Verify that emailSender.send was called with the correct arguments
        verify(emailSender, times(1)).send(any(SimpleMailMessage.class));

        // Verify OTP is stored in the map
        Map<String, String> otpMap = otpService.getOtpMap();
        assertEquals(otp, otpMap.get(userEmail));
    }

    @Test
    public void testIsValidOtpValid() {
        String userEmail = "test@example.com";
        String otp = "123456";

        // Set up a valid OTP in the map
        Map<String, String> otpMap = new HashMap<>();
        otpMap.put(userEmail, otp);
        otpService.setOtpMap(otpMap);

        assertTrue(otpService.isValidOtp(userEmail, otp));
    }

    @Test
    public void testIsValidOtpInvalid() {
        String userEmail = "test@example.com";
        String otp = "123456";
        String invalidOtp = "654321";

        // Set up a valid OTP in the map
        Map<String, String> otpMap = new HashMap<>();
        otpMap.put(userEmail, otp);
        otpService.setOtpMap(otpMap);

        assertFalse(otpService.isValidOtp(userEmail, invalidOtp));
    }
}
