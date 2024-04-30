package com.diagrammingtool.app.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserLoginTest {

    @Test
    public void testConstructorAndGetters() {
        UserLogin userLogin = new UserLogin("john@example.com", "Password123!");
        assertEquals("john@example.com", userLogin.getUserEmail());
        assertEquals("Password123!", userLogin.getPassword());
    }

    @Test
    public void testSetters() {
        UserLogin userLogin = new UserLogin();
        userLogin.setUserEmail("jane@example.com");
        userLogin.setPassword("StrongPassword123!");
        assertEquals("jane@example.com", userLogin.getUserEmail());
        assertEquals("StrongPassword123!", userLogin.getPassword());
    }
}
