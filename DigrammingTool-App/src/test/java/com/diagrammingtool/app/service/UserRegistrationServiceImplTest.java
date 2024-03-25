/**
 * 
 */
package com.diagrammingtool.app.service;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;


import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.diagrammingtool.app.model.UserRegistration;
import com.diagrammingtool.app.repository.UserRegistrationRepository;

/**
 * 
 */

@SpringBootTest
class UserRegistrationServiceImplTest {
        @InjectMocks
	    private UserRegistrationServiceImpl userService;
	    @Mock
	    private UserRegistrationRepository userRepoMock;
	    @Mock
	    private PasswordEncoder passwordEncoder;
	   
	   
		@Test
		public void addUserTest() {
			UserRegistration user=new UserRegistration("sidhu","pk","sidhu@gmail.com","tw535t");
		    String hashedPassword = "hashedPassword";
	        when( passwordEncoder.encode(user.getPassword())).thenReturn(hashedPassword);
		    when(userRepoMock.save(user)).thenReturn(user);
		    UserRegistration user1=userService.CreateNewUser(user);
		    assertEquals(user, user1);
		    assertEquals(hashedPassword, user.getPassword());
		}
		 @Test
		  public void getAllUsersTest() {
			 List<UserRegistration> users=userService.getAllUser();
			 UserRegistration user1=new UserRegistration("sidhu","pk","sidhu@gmail.com","tw535t");
			 UserRegistration user2=new UserRegistration("rahul","s","rahul@gmail.com","djbhbd");
			 users.add(user1);
		     users.add(user2);
		     when(userRepoMock.findAll()).thenReturn(users);
		     List<UserRegistration> retrievedUsers = userService.getAllUser();
		     assertEquals(users.size(), retrievedUsers.size());
		     assertEquals(users, retrievedUsers);
		    			
		    	
		    }
		 

}
