package com.diagrammingtool.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.diagrammingtool.app.model.UserLogin;
import com.diagrammingtool.app.model.UserRegistration;

@Repository
public interface UserRegistrationRepository extends JpaRepository<UserRegistration,Long> {
	UserRegistration findByUserEmail(String userEmail);
}
