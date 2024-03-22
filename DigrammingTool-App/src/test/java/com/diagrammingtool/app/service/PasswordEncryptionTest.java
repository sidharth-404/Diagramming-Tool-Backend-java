package com.diagrammingtool.app.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PasswordEncryptionTest {
	private final PasswordEncryption passwordEncryption = new PasswordEncryption();

	@Test
    public void testHashPassword() {
        String plainTextPassword = "password123";
        String hashedPassword = passwordEncryption.hashPassword(plainTextPassword);
        assertTrue(hashedPassword != null && !hashedPassword.isEmpty() && !hashedPassword.equals(plainTextPassword));
    }

    @Test
    public void testVerifyPassword() {
        String plainTextPassword = "password123";
        String hashedPassword = passwordEncryption.hashPassword(plainTextPassword);
        assertTrue(passwordEncryption.verifyPassword(plainTextPassword, hashedPassword));
        assertFalse(passwordEncryption.verifyPassword("incorrectPassword", hashedPassword));
    }

}
