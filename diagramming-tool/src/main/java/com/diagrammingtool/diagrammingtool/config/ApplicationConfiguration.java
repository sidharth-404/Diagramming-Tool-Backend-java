package com.diagrammingtool.diagrammingtool.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.diagrammingtool.diagrammingtool.model.UserRegistration;
import com.diagrammingtool.diagrammingtool.repository.UserRegistrationRepository;

@Configuration
public class ApplicationConfiguration {
	private final UserRegistrationRepository userRegistrationRepository;
	
	 public ApplicationConfiguration(UserRegistrationRepository userRegistrationRepository) {
	        this.userRegistrationRepository = userRegistrationRepository;
	        
	    }
	 
	 @Bean
	    UserDetailsService userDetailsService() {
	        return username -> userRegistrationRepository.findByUserEmail(username)
	                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
	    }

	    @Bean
	    BCryptPasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

	    @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
	        return config.getAuthenticationManager();
	    }

	    @Bean
	    AuthenticationProvider authenticationProvider() {
	        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

	        authProvider.setUserDetailsService(userDetailsService());
	        authProvider.setPasswordEncoder(passwordEncoder());

	        return authProvider;
	    }
}
