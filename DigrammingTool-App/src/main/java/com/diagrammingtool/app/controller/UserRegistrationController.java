package com.diagrammingtool.app.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;


import com.diagrammingtool.app.model.UserRegistration;
import com.diagrammingtool.app.service.UserRegistrationServiceImpl;

@RestController
@RequestMapping("/api/diagrammingtool")
public class UserRegistrationController {
	@Autowired
	private UserRegistrationServiceImpl userService;
	

	@PostMapping("/addUser")
	public ResponseEntity<?> AddUser(@Valid @RequestBody UserRegistration user, BindingResult result) {
	    if (result.hasErrors()) {
	        List<String> errors = result.getFieldErrors().stream()
	                .map(err -> err.getDefaultMessage())
	                .collect(Collectors.toList());
	        return ResponseEntity.badRequest().body(errors);
	    }
	    try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.CreateNewUser(user));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

	   
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
	    List<String> errors = ex.getBindingResult()
	            .getFieldErrors()
	            .stream()
	            .map(err -> err.getDefaultMessage())
	            .collect(Collectors.toList());
	    return ResponseEntity.badRequest().body(errors);
	}
	
	@GetMapping("/getUsers")
	public ResponseEntity<List<UserRegistration>> getAllUser(){
		return ResponseEntity.ok(userService.getAllUser());
				}

}
