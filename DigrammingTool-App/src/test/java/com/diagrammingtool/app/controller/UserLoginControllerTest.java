package com.diagrammingtool.app.controller;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.diagrammingtool.app.model.UserLogin;
import com.diagrammingtool.app.model.UserRegistration;
import com.diagrammingtool.app.service.UserLoginServiceImpl;
import com.diagrammingtool.app.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UserLoginController.class)
public class UserLoginControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private JwtUtil jwtUtil;
    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserLoginServiceImpl userLoginService;

    @Test
    public void testUserRegistration_Success() throws Exception {
        UserLogin userLogin = new UserLogin("test@example.com", "password");
        Mockito.doNothing().when(userLoginService).loginUser("test@example.com", "password");
        Mockito.when(jwtUtil.generateToken("test@example.com")).thenReturn("mockedToken");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/diagrammingtool/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(userLogin)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("mockedToken"));
    }
  

}

