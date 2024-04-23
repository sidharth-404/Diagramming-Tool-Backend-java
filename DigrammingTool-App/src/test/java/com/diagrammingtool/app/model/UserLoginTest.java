package com.diagrammingtool.app.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserLoginTest {

    @Test
    public void testConstructorAndGetters() {
        // Create a UserLogin object using the constructor
        UserLogin userLogin = new UserLogin("john@example.com", "Password123!");

        // Verify that the constructor sets the fields correctly
        assertEquals("john@example.com", userLogin.getUserEmail());
        assertEquals("Password123!", userLogin.getPassword());
    }

    @Test
    public void testSetters() {
        // Create a UserLogin object
        UserLogin userLogin = new UserLogin();

        // Set the fields using setters
        userLogin.setUserEmail("jane@example.com");
        userLogin.setPassword("StrongPassword123!");

        // Verify that the setters set the fields correctly
        assertEquals("jane@example.com", userLogin.getUserEmail());
        assertEquals("StrongPassword123!", userLogin.getPassword());
    }
}
