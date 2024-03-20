package com.diagrammingtool.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diagrammingtool.app.model.UserRegistartion;

public interface UserRegistrationRepository extends JpaRepository<UserRegistartion,Integer> {

}
