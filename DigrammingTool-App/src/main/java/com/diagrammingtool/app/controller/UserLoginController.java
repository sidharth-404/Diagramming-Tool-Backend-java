package com.diagrammingtool.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;



import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.bind.annotation.CrossOrigin;
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
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\": \"Email and password are required\"}");
                }
    		userLoginService.loginUser(userLogin.getUserEmail(),userLogin.getPassword());
    		String jwtToken = jwtUtil.generateToken(userLogin.getUserEmail());
    		return ResponseEntity.ok().body(jwtToken);
    	}catch(IllegalArgumentException e)
    	{
    		 if (e.getMessage().equals("user not found")) {
                 
                 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"error\": \"user not found\"}");
             }
    		 else {
                 
                 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"error\": \"invalid password\"}");
             }
    	}
    	
      
    }
   
    
}
