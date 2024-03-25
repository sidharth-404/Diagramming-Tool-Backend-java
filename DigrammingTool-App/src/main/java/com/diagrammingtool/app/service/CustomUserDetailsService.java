package com.diagrammingtool.app.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.diagrammingtool.app.model.UserRegistration;
import com.diagrammingtool.app.repository.UserRegistrationRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
	private UserRegistrationRepository repo;
	
	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserRegistration userEntity = repo.findByUserEmail(username);

        return User.withUsername(userEntity.getUserEmail())
                   .password(userEntity.getPassword())
                   .roles("USER") // Assuming all users have the "USER" role
                   .build();
    }

}
