package com.diagrammingtool.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.diagrammingtool.app.model.UserRegistration;
import com.diagrammingtool.app.repository.UserRegistrationRepository;

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {
    @Autowired
	private UserRegistrationRepository userRepo;
    @Autowired
    PasswordEncoder passwordEncoder;
	@Override
	public UserRegistration CreateNewUser(UserRegistration user) {
		 if (emailExists(user.getUserEmail())) {
	            throw new IllegalArgumentException("Email already exists");
	        }
		String password=passwordEncoder.encode(user.getPassword());
		user.setPassword(password);
		// TODO Auto-generated method stub
		return userRepo.save(user);
	}

	@Override
	public List<UserRegistration> getAllUser() {
		// TODO Auto-generated method stub
		return userRepo.findAll();
	}
	
	 private boolean emailExists(String email) {
	        return userRepo.existsByUserEmail(email);
	    }

}
