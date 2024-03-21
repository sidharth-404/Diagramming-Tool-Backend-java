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
		String password=ps.hashPassword(user.getPassword());
		user.setPassword(password);
		// TODO Auto-generated method stub
		return userRepo.save(user);
	}

	@Override
	public List<UserRegistration> getAllUser() {
		// TODO Auto-generated method stub
		return userRepo.findAll();
	}

}
