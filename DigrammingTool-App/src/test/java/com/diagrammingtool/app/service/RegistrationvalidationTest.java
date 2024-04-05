package com.diagrammingtool.app.service;

import com.diagrammingtool.app.controller.UserRegistrationController;
import com.diagrammingtool.app.model.UserRegistration;
import com.diagrammingtool.app.service.UserRegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

<<<<<<< HEAD
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
=======
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class RegistrationvalidationTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserRegistrationServiceImpl userService;
    @InjectMocks
    private UserRegistrationController userController;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private UserRegistrationController userRegistrationController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

   
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

        
        
       

    }


//    @Test
//    public void testAddUserSuccess() throws Exception {
//        UserRegistration user = new UserRegistration("feril","Doe","feril@example.com", "Password@123");
//
//        when(userService.CreateNewUser(any(UserRegistration.class))).thenReturn(user);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/diagrammingtool/addUser")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(user)))
//                .andExpect(MockMvcResultMatchers.status().isCreated())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.userEmail").value("feril@example.com"));
//    }
    
//    @Test
//    public void testHandleValidationExceptions() throws Exception {
//        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, null);
//        ex.getBindingResult().rejectValue("fieldName1", "errorCode1", "Error Message 1");
//        ex.getBindingResult().rejectValue("fieldName2", "errorCode2", "Error Message 2");
//
//        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/test")
//                .content("{}")
//                .contentType("application/json"))
//                .andReturn();
//
//        ResponseEntity<?> responseEntity = ResponseEntity.badRequest().body(mvcResult.getResolvedException().getMessage());
//
//        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
//        List<String> expectedErrors = Arrays.asList("Error Message 1", "Error Message 2");
//        assertEquals(expectedErrors, responseEntity.getBody());
//    }

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
