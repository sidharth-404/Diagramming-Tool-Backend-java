package com.diagrammingtool.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diagrammingtool.app.model.UserLogin;

public interface UserLoginRepository extends JpaRepository<UserLogin,Integer>{
	 UserLogin findByUserEmail(String userEmail);

}
