package com.diagrammingtool.diagrammingtool.exception;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;

class GlobalExceptionHandlerTest {

	  private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

	    @Test
	    void handleSecurityException_BadCredentialsException_Returns401() {
	        // Arrange
	        BadCredentialsException exception = new BadCredentialsException("Bad credentials");

	        // Act
	        ProblemDetail response  = exceptionHandler.handleSecurityException(exception);

	        // Assert
	        assertEquals(401, response.getStatus());
	        assertEquals("Bad credentials", response.getDetail());
	    }
	    
	    @Test
	    void handleSecurityException_AccountStatusException_Returns403() {
	        // Arrange
	        AccountStatusException exception = new AccountStatusException("Account is locked") {};

	        // Act
	        ProblemDetail response = exceptionHandler.handleSecurityException(exception);

	        // Assert
	        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());
	        assertEquals("Account is locked", response.getDetail());
	     
	    }
	    
	    @Test
	    void handleSecurityException_ErrorDetailsNull_ReturnsInternalServerError() {
	        // Arrange
	        Exception exception = new Exception("Internal Server Error");

	        // Act
	        ProblemDetail response = exceptionHandler.handleSecurityException(exception);

	        // Assert
	        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), response.getStatus());
	        assertEquals("Internal Server Error", response.getDetail());
	      
	    }


}
