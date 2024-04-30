package com.diagrammingtool.diagrammingtool.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.diagrammingtool.diagrammingtool.dto.UserLoginDto;
import com.diagrammingtool.diagrammingtool.model.UserRegistration;
import com.diagrammingtool.diagrammingtool.repository.UserRegistrationRepository;

@Service
public class UserRegistrationService {
	
private final UserRegistrationRepository userRepository;
private final PasswordEncoder passwordEncoder;
private final AuthenticationManager authenticationManager;

public UserRegistrationService(
        UserRegistrationRepository userRepository,
        AuthenticationManager authenticationManager,
        PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

     public UserRegistration createNewUser(UserRegistration input) {
    	 
    	 if (emailExists(input.getUserEmail())) {
	            throw new IllegalArgumentException("Email already exists");
	        }
           input.setPassword(passwordEncoder.encode(input.getPassword()));
    	 return userRepository.save(input);
     }
     
     public UserRegistration authenticate(UserLoginDto input) {
    	 
    	Optional<UserRegistration> user = userRepository.findByUserEmail(input.getUserEmail());
    	 if(user.isEmpty())
	        {
	        	throw new IllegalArgumentException("user not found");
	        }
	        if( passwordEncoder.matches(input.getPassword(),user.get().getPassword())==false) {
	        	throw new IllegalArgumentException("invalid password");
	        }
    	 
         authenticationManager.authenticate(
                 new UsernamePasswordAuthenticationToken(
                         input.getUserEmail(),
                         input.getPassword()) 
         );
         return user.get();

     }
     
           public void changeUserPassword(String userEmail, String newPassword) {
	        UserRegistration user =userRepository.findByUserEmail(userEmail).get();
	        if (user == null) {
	            throw new IllegalArgumentException("User not found");
	        }
	        user.setPassword(passwordEncoder.encode(newPassword));
	        userRepository.save(user);
	    }
     
          public boolean verifyUserPassword(String userEmail, String password) {
	        UserRegistration user = userRepository.findByUserEmail(userEmail).get();
	        if (user == null) {
	            throw new IllegalArgumentException("User not found");
	        }
	        return passwordEncoder.matches(password, user.getPassword());
	    }
     
            public boolean emailExists(String email) {
	        return userRepository.existsByUserEmail(email);
	    }

}
