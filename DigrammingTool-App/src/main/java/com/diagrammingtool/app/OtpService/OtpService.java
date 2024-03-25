package com.diagrammingtool.app.OtpService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class OtpService {
	
	@Autowired
    private final JavaMailSender emailSender;

    private final Map<String, String> otpMap = new HashMap<>();

    @Autowired
    public OtpService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public String generateOtp() {
        // Generate a 6-digit OTP
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    public void sendOtpEmail(String userEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userEmail);
        message.setSubject("OTP for Password Reset");
        message.setText("Your OTP for password reset is: " + otp);

        emailSender.send(message);

        // Store OTP in map for verification later
        otpMap.put(userEmail, otp);
    }

    public boolean isValidOtp(String userEmail, String otp) {
        // Get stored OTP from map
        String storedOtp = otpMap.get(userEmail);
        return storedOtp != null && storedOtp.equals(otp);
    }
}
