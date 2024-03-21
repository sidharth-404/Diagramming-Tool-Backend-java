package com.diagrammingtool.app.validation;

import com.diagrammingtool.app.controller.UserRegistrationController;
import com.diagrammingtool.app.model.UserRegistration;
import com.diagrammingtool.app.service.UserRegistrationServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(UserRegistrationController.class)
@AutoConfigureMockMvc
public class RegistrationvalidationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRegistrationServiceImpl userService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testAddUserSuccess() throws Exception {
        UserRegistration user = new UserRegistration("feril","Doe","feril@example.com", "Password@123");

        when(userService.CreateNewUser(any(UserRegistration.class))).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/diagrammingtool/addUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userEmail").value("feril@example.com"));
    }

    @Test
    public void testAddUserValidationFailure() throws Exception {
        UserRegistration user = new UserRegistration("John", "Doe", "invalidemail", "passWord123@");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/diagrammingtool/addUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").value("Invalid email format"));
//                .andExpect(MockMvcResultMatchers.jsonPath("$[1]").value("Password must contain at least 8 characters including one uppercase letter one lowercase letter one number and one special character"));
    }

    @Test
    public void testAddUserEmailExists() throws Exception {
        UserRegistration user = new UserRegistration("Jaaak", "dd", "feril@example.com", "Password@123");

        when(userService.CreateNewUser(any(UserRegistration.class))).thenThrow(IllegalArgumentException.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/diagrammingtool/addUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Email already exists"));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        List<UserRegistration> users = new ArrayList<>();
        users.add(new UserRegistration("sidharth", "pK", "sid@example.com", "Password@123"));
        users.add(new UserRegistration("Abin", "James", "abinjames@example.com", "Password@456"));

        when(userService.getAllUser()).thenReturn(users);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/diagrammingtool/getUsers"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userEmail").value("sid@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].userEmail").value("abinjames@example.com"));
    }
}
