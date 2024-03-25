package com.diagrammingtool.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diagrammingtool.app.model.UserLogin;
import com.diagrammingtool.app.repository.UserRegistrationRepository;
import com.diagrammingtool.app.service.UserLoginServiceImpl;
import com.diagrammingtool.app.util.JwtUtil;


@RestController
@RequestMapping("/api/diagrammingtool")
public class UserLoginController {
	@Autowired
    private UserLoginServiceImpl userLoginService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	 @Autowired
	 private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> UserRegistration(@RequestBody UserLogin userLogin) {
    	try {
    		userLoginService.loginUser(userLogin.getUserEmail(),userLogin.getPassword());
    		String jwtToken = jwtUtil.generateToken(userLogin.getUserEmail());
    		return ResponseEntity.ok(jwtToken);
    	}catch(IllegalArgumentException e)
    	{
    		return ResponseEntity.badRequest().body(e.getMessage());
    	}
      
    }
   
    
  
    

}
