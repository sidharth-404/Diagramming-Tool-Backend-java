package com.diagrammingtool.app.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.diagrammingtool.app.model.UserLogin;
import com.diagrammingtool.app.repository.UserLoginRepository;

public class UserLoginServiceImpl implements UserLoginService{
	 @Autowired
	    private UserLoginRepository userLoginRepository;

	    @Override
	    public UserLogin loginUser(String userEmail, String password) {
	        UserLogin user = userLoginRepository.findByUserEmail(userEmail);

	        if (user != null && user.getPassword().equals(password)) {
	            return user;
	        } else {
	            return null;
	        }
	    }

}
