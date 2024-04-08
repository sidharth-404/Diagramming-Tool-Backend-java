package com.diagrammingtool.app.service;

import com.diagrammingtool.app.validation.PasswordValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import javax.validation.ConstraintValidatorContext;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PasswordValidatorTest {

    private PasswordValidator validator;
    private ConstraintValidatorContext context;

    @BeforeEach
    public void setup() {
        validator = new PasswordValidator();
        context = null; // Not used in these tests, can be null
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "Abcd@123", // Valid password
            "Abcd@1234", // Valid password
            "Test@123!", // Valid password
            "Hello123!", // Valid password
            "12345678aA!", // Valid password
            "Short1!", // Invalid, less than 8 characters
           
    })
    public void testIsValid(String password) {
        boolean result = validator.isValid(password, context);
        if (password != null && !password.isEmpty() && password.length() >= 8) {
            assertTrue(result, "Expected '" + password + "' to be valid");
        } else {
            assertFalse(result, "Expected '" + password + "' to be invalid");
        }
    }

    @ParameterizedTest
    @NullSource
    @EmptySource
    public void testIsNullEmpty(String password) {
        assertFalse(validator.isValid(password, context), "Expected null or empty password to be invalid");
    }
}
