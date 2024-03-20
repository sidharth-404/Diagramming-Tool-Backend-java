package com.diagrammingtool.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.diagrammingtool.app.model.UserRegistration;
import com.diagrammingtool.app.service.UserRegsitartionServiceImpl;

@RestController
@RequestMapping("/api/diagrammingtool")
public class UserRegistrationController {
	@Autowired
	private UserRegsitartionServiceImpl userService;
	
	@PostMapping("/addUser")
	public ResponseEntity<UserRegistration> AddUser(@RequestBody  UserRegistration User) {
		return ResponseEntity.ok(userService.CreateNewUser(User));
		
	}
	
	@GetMapping("/getUsers")
	public ResponseEntity<List<UserRegistration>> getAllUser(){
		return ResponseEntity.ok(userService.getAllUser());
				}

}
