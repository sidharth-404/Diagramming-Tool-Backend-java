package com.diagrammingtool.app.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.diagrammingtool.app.model.UserRegistration;
import com.diagrammingtool.app.service.UserRegistrationServiceImpl;



@SpringBootTest
class UserRegistrationControllerTest {
	@Mock
    private UserRegistrationServiceImpl userService;
	 @InjectMocks
	 private UserRegistrationController userController;

	 
	@Test
	public void testGetAllUser() {
		List<UserRegistration> users = new ArrayList<>();
		UserRegistration user1 = new UserRegistration("sidhu","pk","sidhu@gmail.com","tw535t");
		when(userService.getAllUser()).thenReturn(users);
		ResponseEntity<List<UserRegistration>> response=userController.getAllUser();
		assertEquals(HttpStatus.OK,response.getStatusCode());	
		
	}

}
