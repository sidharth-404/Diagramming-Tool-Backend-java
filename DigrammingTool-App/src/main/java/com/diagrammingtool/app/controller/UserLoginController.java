package com.diagrammingtool.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diagrammingtool.app.model.UserLogin;
import com.diagrammingtool.app.model.UserRegistration;
import com.diagrammingtool.app.service.UserLoginServiceImpl;

@RestController
@RequestMapping("/api/diagrammingtool")
@CrossOrigin(origins = "http://localhost:3001")
public class UserLoginController {
    @Autowired
    private UserLoginServiceImpl userLoginService;

    @PostMapping("/login")
    public ResponseEntity<?> userRegistration(@RequestBody UserLogin userLogin) {
        try {
            if (userLogin.getUserEmail().isEmpty() || userLogin.getPassword().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\": \"Email and password are required\"}");
            }
           
            UserRegistration user = userLoginService.loginUser(userLogin.getUserEmail(), userLogin.getPassword());
           
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("user not found")) {
               
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"error\": \"user not found\"}");
            } else {
              
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"error\": \"invalid password\"}");
            }
        }
    }

}
