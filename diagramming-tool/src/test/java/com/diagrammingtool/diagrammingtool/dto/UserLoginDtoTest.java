package com.diagrammingtool.diagrammingtool.dto;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UserLoginDtoTest {


    @Test
    public void testGettersAndSetters() {
        // Create a UserLoginDto object
        UserLoginDto userLoginDto = new UserLoginDto();

        // Set values using setters
        userLoginDto.setUserEmail("test@example.com");
        userLoginDto.setPassword("password123");

        // Verify values using getters
        assertEquals("test@example.com", userLoginDto.getUserEmail());
        assertEquals("password123", userLoginDto.getPassword());
    }

}
