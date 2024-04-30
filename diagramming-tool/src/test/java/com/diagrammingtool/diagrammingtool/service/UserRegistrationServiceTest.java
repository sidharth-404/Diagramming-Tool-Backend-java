package com.diagrammingtool.diagrammingtool.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.diagrammingtool.diagrammingtool.dto.UserLoginDto;
import com.diagrammingtool.diagrammingtool.model.UserRegistration;
import com.diagrammingtool.diagrammingtool.repository.UserRegistrationRepository;

class UserRegistrationServiceTest {

	 @Test
	    public void testCreateNewUser() {
	      
	        UserRegistrationRepository userRepository = mock(UserRegistrationRepository.class);
	        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
	        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);

	        // Create service instance
	        UserRegistrationService userService = new UserRegistrationService(userRepository, authenticationManager, passwordEncoder);

	        // Prepare test data
	        UserRegistration newUser = new UserRegistration();
	        newUser.setUserEmail("test@example.com");
	        newUser.setPassword("password");

	        // Mock behavior
	        when(userRepository.existsByUserEmail(anyString())).thenReturn(false);
	        when(userRepository.save(any(UserRegistration.class))).thenReturn(newUser);
	        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

	        // Test
	        UserRegistration result = userService.createNewUser(newUser);

	        // Verify
	        assertNotNull(result);
	        assertEquals("test@example.com", result.getUserEmail());
	        assertEquals("encodedPassword", result.getPassword());
	    }
	 
	 
	 @Test
	    public void testAuthenticate() {
	        // Mock dependencies
	        UserRegistrationRepository userRepository = mock(UserRegistrationRepository.class);
	        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
	        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);

	        // Create service instance
	        UserRegistrationService userService = new UserRegistrationService(userRepository, authenticationManager, passwordEncoder);

	        // Prepare test data
	        UserLoginDto loginDto = new UserLoginDto();
	        loginDto.setUserEmail("test@example.com");
	        loginDto.setPassword("password");

	        UserRegistration user = new UserRegistration();
	        user.setUserEmail("test@example.com");
	        user.setPassword("encodedPassword");

	        // Mock behavior
	        when(userRepository.findByUserEmail(anyString())).thenReturn(Optional.of(user));
	        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

	        // Test
	        UserRegistration result = userService.authenticate(loginDto);

	        // Verify
	        assertNotNull(result);
	        assertEquals("test@example.com", result.getUserEmail());
	        assertEquals("encodedPassword", result.getPassword());
	    }
	 
	 @Test
	    public void testChangeUserPassword() {
	        // Mock dependencies
	        UserRegistrationRepository userRepository = mock(UserRegistrationRepository.class);
	        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
	        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);

	        // Create service instance
	        UserRegistrationService userService = new UserRegistrationService(userRepository, authenticationManager, passwordEncoder);

	        // Prepare test data
	        String userEmail = "test@example.com";
	        String newPassword = "newPassword";

	        UserRegistration user = new UserRegistration();
	        user.setUserEmail(userEmail);
	        user.setPassword("oldEncodedPassword");

	        // Mock behavior
	        when(userRepository.findByUserEmail(anyString())).thenReturn(Optional.of(user));
	        when(passwordEncoder.encode(anyString())).thenReturn("newEncodedPassword");

	        // Call the method under test
	        userService.changeUserPassword(userEmail, newPassword);

	        // Verify that the password was changed
	        verify(userRepository, times(1)).findByUserEmail(userEmail);
	        verify(passwordEncoder, times(1)).encode(newPassword);
	        assertEquals("newEncodedPassword", user.getPassword());
	        verify(userRepository, times(1)).save(user);
	    }
	 
	 @Test
	    public void testVerifyUserPassword() {
	        // Mock dependencies
	        UserRegistrationRepository userRepository = mock(UserRegistrationRepository.class);
	        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
	        AuthenticationManager authenticationManager = mock(AuthenticationManager.class);

	        // Create service instance
	        UserRegistrationService userService = new UserRegistrationService(userRepository, authenticationManager, passwordEncoder);

	        // Prepare test data
	        String userEmail = "test@example.com";
	        String password = "password";

	        UserRegistration user = new UserRegistration();
	        user.setUserEmail(userEmail);
	        user.setPassword("encodedPassword");

	        // Mock behavior
	        when(userRepository.findByUserEmail(anyString())).thenReturn(Optional.of(user));
	        when(passwordEncoder.matches(eq(password), anyString())).thenReturn(true);

	        // Call the method under test
	        boolean result = userService.verifyUserPassword(userEmail, password);

	        // Verify
	        assertTrue(result);
	    }




}
