package com.diagrammingtool.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.diagrammingtool.app.model.UserLogin;
import com.diagrammingtool.app.model.UserRegistration;
import com.diagrammingtool.app.repository.UserRegistrationRepository;


@Service
public class UserLoginServiceImpl implements UserLoginService{
	 @Autowired
	 private UserRegistrationRepository userDetails;
	 @Autowired
	 PasswordEncoder passwordEncoder;

	    @Override
	    public void loginUser(String userEmail, String password) {
	        UserRegistration user = userDetails.findByUserEmail(userEmail);
	        
	        if(user== null)
	        {
	        	throw new IllegalArgumentException("user not found");
	        }
	        if( passwordEncoder.matches(password,user.getPassword())==false) {
	        	throw new IllegalArgumentException("invalid password");
	        }
	       }
}
