package com.diagrammingtool.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diagrammingtool.app.model.UserLogin;
import com.diagrammingtool.app.model.UserRegistration;
import com.diagrammingtool.app.repository.UserRegistrationRepository;


@Service
public class UserLoginServiceImpl implements UserLoginService{
	 @Autowired
	 private UserRegistrationRepository userDetails;
	 
	 @Autowired
	 private PasswordEncryption passwordEncrypt;

	    @Override
	    public UserRegistration loginUser(String userEmail, String password) {
	        UserRegistration user = userDetails.findByUserEmail(userEmail);
	        
	        if(user== null)
	        {
	        	throw new IllegalArgumentException("user not found");
	        }
	        if(passwordEncrypt.verifyPassword(password,user.getPassword())==false) {
	        	throw new IllegalArgumentException("invalid password");
	        }

	        return user;
	    }

}
