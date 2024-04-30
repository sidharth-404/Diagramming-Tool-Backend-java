package com.diagrammingtool.diagrammingtool.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserRegistrationTest {

	private UserRegistration userRegistration;

    @BeforeEach
    public void setUp() {
        userRegistration = new UserRegistration("John", "Doe", "john@example.com", "password123");
        userRegistration.setUserId(1L);
        userRegistration.setCreatedAt(new Date());
        userRegistration.setUpdatedAt(new Date());
    }

    @Test
    public void testGettersAndSetters() {
        assertEquals(1L, userRegistration.getUserId());
        assertEquals("John", userRegistration.getFirstName());
        assertEquals("Doe", userRegistration.getLastName());
        assertEquals("john@example.com", userRegistration.getUserEmail());
        assertEquals("password123", userRegistration.getPassword());
        assertNotNull(userRegistration.getCreatedAt());
        assertNotNull(userRegistration.getUpdatedAt());
    }

    @Test
    public void testAuthorities() {
        assertTrue(userRegistration.getAuthorities().isEmpty());
    }

    @Test
    public void testUsername() {
        assertEquals("john@example.com", userRegistration.getUsername());
    }

    @Test
    public void testAccountNonExpired() {
        assertTrue(userRegistration.isAccountNonExpired());
    }

    @Test
    public void testAccountNonLocked() {
        assertTrue(userRegistration.isAccountNonLocked());
    }

    @Test
    public void testCredentialsNonExpired() {
        assertTrue(userRegistration.isCredentialsNonExpired());
    }

    @Test
    public void testEnabled() {
        assertTrue(userRegistration.isEnabled());
    }
}
