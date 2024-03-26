package com.diagrammingtool.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diagrammingtool.app.model.UserRegistration;
import com.diagrammingtool.app.repository.UserRegistrationRepository;

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {
    @Autowired
	private UserRegistrationRepository userRepo;
    @Autowired
    private PasswordEncryption ps;
	@Override
	public UserRegistration CreateNewUser(UserRegistration user) {
		 if (emailExists(user.getUserEmail())) {
	            throw new IllegalArgumentException("Email already exists");
	        }
		String password=ps.hashPassword(user.getPassword());
		user.setPassword(password);
		
		return userRepo.save(user);
	}

	@Override
	public List<UserRegistration> getAllUser() {
		
		return userRepo.findAll();
	}
	
	 public UserRegistration getUserByEmail(String userEmail) {
	        return userRepo.findByUserEmail(userEmail);
	    }

	    public UserRegistration updateUser(UserRegistration user) {
	    	String password=ps.hashPassword(user.getPassword());
			user.setPassword(password);
	        return userRepo.save(user);
	    }
	
	 private boolean emailExists(String email) {
	        return userRepo.existsByUserEmail(email);
	    }

}
