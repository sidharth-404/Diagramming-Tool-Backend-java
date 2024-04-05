package com.diagrammingtool.app.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;



import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diagrammingtool.app.model.UserLogin;

import com.diagrammingtool.app.repository.UserRegistrationRepository;

import com.diagrammingtool.app.model.UserRegistration;

import com.diagrammingtool.app.service.UserLoginServiceImpl;
import com.diagrammingtool.app.util.JwtUtil;

@RestController
@RequestMapping("/api/diagrammingtool")



@CrossOrigin("*")

public class UserLoginController {
    @Autowired
    private UserLoginServiceImpl userLoginService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	 @PostMapping("/login")
    public ResponseEntity<?> UserRegistration(@RequestBody UserLogin userLogin) {
    
    		try {
                if (userLogin.getUserEmail().isEmpty() || userLogin.getPassword().isEmpty()) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email and password are required");
                }
    		userLoginService.loginUser(userLogin.getUserEmail(),userLogin.getPassword());
    		String jwtToken = jwtUtil.generateToken(userLogin.getUserEmail());
    		return ResponseEntity.ok().body(jwtToken);
    	}catch(IllegalArgumentException e)
    	{
    		 if (e.getMessage().equals("user not found")) {
                 
                 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("user not found");
             }
    		 else {
                 
                 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid password");
             }
    	}
    	
      
    }
    
    
    @PatchMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody Map<String, String> requestBody) {
        try {
            String userEmail = requestBody.get("userEmail");
            String currentPassword = requestBody.get("currentPassword");
            String newPassword = requestBody.get("newPassword");
            String confirmPassword = requestBody.get("confirmPassword");
            String jwtToken=requestBody.get("jwtToken");
            
         System.out.println(jwtToken);
            // Verify the user's current password
            if (!userLoginService.verifyUserPassword(userEmail, currentPassword)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect current password");
            }
       
            // Validate the new password and confirm password
            if (!newPassword.equals(confirmPassword)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("New password and confirm password do not match");
            } 
           String userEmailToken= jwtUtil.getUsernameFromToken(jwtToken);
           if(!userEmail.equals(userEmailToken)) {
        	   return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized email");
        	   
           }
           
            // Change the user's password
            userLoginService.changeUserPassword(userEmail, newPassword);
 
            return ResponseEntity.ok("Password changed successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
   
    
}
