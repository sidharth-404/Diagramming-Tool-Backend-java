package com.diagrammingtool.app.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

        when(userLoginService.loginUser(any(String.class), any(String.class))).thenReturn(new UserRegistration());
        mockMvc.perform(post("/api/diagrammingtool/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userLogin)))
                .andExpect(status().isOk());
    }
}


