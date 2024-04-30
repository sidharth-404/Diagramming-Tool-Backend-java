package com.diagrammingtool.diagrammingtool.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.diagrammingtool.diagrammingtool.model.UserRegistration;

@Repository
public interface UserRegistrationRepository extends JpaRepository<UserRegistration,Long> {
	@Query(value = "SELECT * FROM user_registration u WHERE u.user_email = :userEmail", nativeQuery = true)
	Optional<UserRegistration> findByUserEmail(String userEmail);
	boolean existsByUserEmail(String email);
}
