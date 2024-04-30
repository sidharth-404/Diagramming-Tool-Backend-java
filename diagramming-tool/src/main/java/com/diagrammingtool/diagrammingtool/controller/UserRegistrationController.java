package com.diagrammingtool.diagrammingtool.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.diagrammingtool.diagrammingtool.dto.UserLoginDto;
import com.diagrammingtool.diagrammingtool.model.UserRegistration;
import com.diagrammingtool.diagrammingtool.service.JwtService;
import com.diagrammingtool.diagrammingtool.service.UserRegistrationService;

import jakarta.validation.Valid;


@RequestMapping("/api")
@RestController
@CrossOrigin("*")
public class UserRegistrationController {
     
	private final JwtService jwtService;
    private final UserRegistrationService service;
    public UserRegistrationController(JwtService jwtService, UserRegistrationService service) {
        this.jwtService = jwtService;
        this.service = service;
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> register (@RequestBody UserRegistration input)
    {
    	
    	 try {
             return ResponseEntity.status(HttpStatus.CREATED).body(service.createNewUser(input));
         }
    	 catch (IllegalArgumentException e) {
             return ResponseEntity.badRequest().body("Email already exists");
         }
    	
    }
    
    
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody UserLoginDto userLogin) {
    	try {
                if (userLogin.getUserEmail().isEmpty() || userLogin.getPassword().isEmpty()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email and password are required");
                }
        UserRegistration authenticatedUser = service.authenticate(userLogin);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        		return ResponseEntity.status(HttpStatus.OK).body(jwtToken);
    	}
    		catch(IllegalArgumentException e)
        	{
        		 if (e.getMessage().equals("user not found")) {
                     
                     return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("user not found");
                 }
        		 else {
                     
                     return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid password");
                 }
        	}
    	
}
    
   
}
