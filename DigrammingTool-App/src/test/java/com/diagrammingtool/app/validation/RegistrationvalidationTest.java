package com.diagrammingtool.app.validation;

import com.diagrammingtool.app.controller.UserRegistrationController;
import com.diagrammingtool.app.model.UserRegistration;
import com.diagrammingtool.app.service.UserRegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RegistrationvalidationTest {

    @Mock
    private UserRegistrationServiceImpl userService;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private UserRegistrationController userRegistrationController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddUserValidInput() {
        UserRegistration validUser = new UserRegistration();
        validUser.setUserEmail("test@example.com");
        validUser.setPassword("password");

        // Mocking the binding result to indicate no errors
        when(bindingResult.hasErrors()).thenReturn(false);

        // Mocking the userService to return the created user
        when(userService.CreateNewUser(any(UserRegistration.class))).thenReturn(validUser);

        ResponseEntity<?> responseEntity = userRegistrationController.AddUser(validUser, bindingResult);

        // Verify that userService.create was called once with the validUser
        verify(userService, times(1)).CreateNewUser(validUser);

        // Verify the response status and body
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(validUser, responseEntity.getBody());
    }

    @Test
    public void testAddUserInvalidInput() {
        UserRegistration invalidUser = new UserRegistration();
        invalidUser.setUserEmail(""); // Invalid email
        invalidUser.setPassword("password");

        // Mocking the binding result to indicate errors
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getFieldErrors()).thenReturn(new ArrayList<>());

        ResponseEntity<?> responseEntity = userRegistrationController.AddUser(invalidUser, bindingResult);

        // Verify that userService.create was not called since there are errors
        verify(userService, never()).CreateNewUser(invalidUser);

        // Verify the response status and body
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        // In this case, the body should contain a list of validation errors
        assertEquals(ArrayList.class, responseEntity.getBody().getClass());
    }

    @Test
    public void testAddUserExistingEmail() {
        UserRegistration existingUser = new UserRegistration();
        existingUser.setUserEmail("existing@example.com");
        existingUser.setPassword("password");

        // Mocking the binding result to indicate no errors
        when(bindingResult.hasErrors()).thenReturn(false);

        // Mocking the userService to throw an exception for existing email
        when(userService.CreateNewUser(any(UserRegistration.class)))
                .thenThrow(new IllegalArgumentException("Email already exists"));

        ResponseEntity<?> responseEntity = userRegistrationController.AddUser(existingUser, bindingResult);

        // Verify that userService.create was called once with the existingUser
        verify(userService, times(1)).CreateNewUser(existingUser);

        // Verify the response status and body
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Email already exists", responseEntity.getBody());
    }
}
