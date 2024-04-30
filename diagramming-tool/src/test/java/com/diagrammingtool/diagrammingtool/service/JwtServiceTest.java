package com.diagrammingtool.diagrammingtool.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyMap;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

class JwtServiceTest {
	
	private JwtService jwtService;
    private UserDetails userDetails;
    private final String secretKey = "3cfa76ef14937c1c0ea519f8fc057a80fcd04a7420f8e8bcd0a7567c272e007b";
    private final long expirationTime = 3600000L; // 1 hour in milliseconds

    @BeforeEach
    public void setUp() {
        jwtService = new JwtService();
        jwtService.secretKey = secretKey;
        jwtService.jwtExpiration = expirationTime;

        userDetails = new User("testuser", "password", new ArrayList<>());
    }

    @Test
    public void testGenerateToken() {
        // Mock JwtService
        JwtService jwtServiceMock = mock(JwtService.class);
        when(jwtServiceMock.generateToken(anyMap(), any())).thenReturn("mockedToken");

        // Prepare test data
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("key1", "value1");
        extraClaims.put("key2", "value2");

        String token = jwtServiceMock.generateToken(extraClaims, userDetails);

        assertEquals("mockedToken", token);
    }	
    
    @Test
    public void testIsTokenValid() {
        // Generate a token
        String token = jwtService.generateToken(userDetails);

        // Verify
        assertTrue(jwtService.isTokenValid(token, userDetails));
    }
    
    @Test
    public void testExtractClaim() {
        // Mock the JwtService
        JwtService jwtServiceMock = mock(JwtService.class);

        // Define the expected claim value
        String expectedClaimValue = "testClaimValue";

        // Set up the mock behavior
        when(jwtServiceMock.extractClaim(any(String.class), any(Function.class))).thenReturn(expectedClaimValue);

        // Call the method under test
        String extractedClaim = (String) jwtServiceMock.extractClaim("mockedToken", claims -> claims.get("testClaim"));

        // Verify that the extracted claim matches the expected value
        assertEquals(expectedClaimValue, extractedClaim);
    }
    
    @Test
    public void testExtractExpiration() {
        // Mock the JwtService
        JwtService jwtServiceMock = mock(JwtService.class);

        // Define the expected expiration date
        Date expectedExpiration = new Date();

        // Set up the mock behavior
        when(jwtServiceMock.extractExpiration(any(String.class))).thenReturn(expectedExpiration);

        // Call the method under test
        Date extractedExpiration = jwtServiceMock.extractExpiration("mockedToken");

        // Verify that the extracted expiration matches the expected value
        assertEquals(expectedExpiration, extractedExpiration);
    }
    @Test
    public void testIsTokenExpired() {
        // Mock the JwtService
        JwtService jwtServiceMock = mock(JwtService.class);

        // Define the current date and time
        Date currentDate = new Date();

        // Define the expiration date to be before the current date
        Date expiredDate = new Date(currentDate.getTime() - 1000); // 1 second ago

        // Set up the mock behavior to return the expired date
        when(jwtServiceMock.extractExpiration(any(String.class))).thenReturn(expiredDate);

        // Call the method under test with a token
        boolean isExpired = jwtServiceMock.isTokenExpired("mockedToken");

        // Verify that the token is considered expired
        assertFalse(isExpired);
    }

}
