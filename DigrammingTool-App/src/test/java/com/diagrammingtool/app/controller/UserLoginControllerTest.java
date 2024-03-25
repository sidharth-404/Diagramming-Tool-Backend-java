package com.diagrammingtool.app.controller;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.diagrammingtool.app.model.UserLogin;
import com.diagrammingtool.app.model.UserRegistration;
import com.diagrammingtool.app.service.UserLoginServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UserLoginController.class)
public class UserLoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserLoginServiceImpl userLoginService;

    @Test
    public void testUserRegistration_Success() throws Exception {
        UserLogin userLogin = new UserLogin("test@example.com", "password");
        UserRegistration userRegistration = new UserRegistration();
        when(userLoginService.loginUser(any(String.class), any(String.class))).thenReturn(userRegistration);

        mockMvc.perform(post("/api/diagrammingtool/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userLogin)))
                .andExpect(status().isOk())
                .andExpect(content().json("{}")); // Adjust this to match the response
    }

    @Test
    public void testUserRegistration_UserNotFound() throws Exception {
        UserLogin userLogin = new UserLogin("nonexistent@example.com", "password");
        when(userLoginService.loginUser(any(String.class), any(String.class))).thenThrow(new IllegalArgumentException("user not found"));

        mockMvc.perform(post("/api/diagrammingtool/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userLogin)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().json("{\"error\": \"user not found\"}"));
    }

    @Test
    public void testUserRegistration_InvalidPassword() throws Exception {
        UserLogin userLogin = new UserLogin("test@example.com", "wrongpassword");
        when(userLoginService.loginUser(any(String.class), any(String.class))).thenThrow(new IllegalArgumentException("invalid password"));

        mockMvc.perform(post("/api/diagrammingtool/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userLogin)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().json("{\"error\": \"invalid password\"}"));
    }

    @Test
    public void testUserRegistration_EmptyEmail() throws Exception {
        UserLogin userLogin = new UserLogin("", "password");

        mockMvc.perform(post("/api/diagrammingtool/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userLogin)))
                .andExpect(status().isBadRequest()); // Assuming empty email should return bad request
    }

    @Test
    public void testUserRegistration_EmptyPassword() throws Exception {
        UserLogin userLogin = new UserLogin("test@example.com", "");

        mockMvc.perform(post("/api/diagrammingtool/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userLogin)))
                .andExpect(status().isBadRequest()); // Assuming empty password should return bad request
    }
}

