package com.diagrammingtool.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diagrammingtool.app.model.UserLogin;
import com.diagrammingtool.app.service.UserLoginServiceImpl;


@RestController
@RequestMapping("/api/diagrammingtool")
@CrossOrigin(origins = "*")
public class UserLoginController {
	@Autowired
    private UserLoginServiceImpl userLoginService;

    @PostMapping("/login")
    public ResponseEntity<?> UserRegistration(@RequestBody UserLogin userLogin) {
    	try {
    		
    		return ResponseEntity.ok(userLoginService.loginUser(userLogin.getUserEmail(), userLogin.getPassword()));
    	}catch(IllegalArgumentException e)
    	{
    		return ResponseEntity.badRequest().body(e.getMessage());
    	}
      
    }
    
    

}
