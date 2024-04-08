package com.diagrammingtool.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.diagrammingtool.app.model.UserRegistration;
import com.diagrammingtool.app.repository.UserRegistrationRepository;

public class UserDetailsServiceImplTest {

    private UserDetailsServiceImpl userDetailsService;
    private UserRegistrationRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository = mock(UserRegistrationRepository.class);
        userDetailsService = new UserDetailsServiceImpl(userRepository);
    }

    @Test
    public void testLoadUserByUsername_ExistingUser() {
        // Mock data
        String userEmail = "test@example.com";
        String password = "password";
        UserRegistration user = new UserRegistration();
        user.setUserEmail(userEmail);
        user.setPassword(password);

        // Mock repository behavior
        when(userRepository.findByUserEmail(userEmail)).thenReturn(user);

        // Call the service method
        UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

        // Verify
        assertEquals(userEmail, userDetails.getUsername());
        assertEquals(password, userDetails.getPassword());
        assertEquals("USER", userDetails.getAuthorities().iterator().next().getAuthority());
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        // Mock data
        String userEmail = "nonexisting@example.com";

        // Mock repository behavior (return null)
        when(userRepository.findByUserEmail(userEmail)).thenReturn(null);

        // Call the service method and expect an exception
        try {
            userDetailsService.loadUserByUsername(userEmail);
        } catch (UsernameNotFoundException e) {
            assertEquals("User not found with email: " + userEmail, e.getMessage());
        }
    }
}
