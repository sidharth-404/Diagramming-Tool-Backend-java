package com.diagrammingtool.app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.diagrammingtool.app.validation.PasswordConstraint;

@Entity
public class UserRegistration {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;
  
  @NotEmpty(message = "First name is required")
  @Size(min = 2, max = 20, message = "First name must be between 2 and 20 characters")
  private String firstName;
  
  private String lastName;
  
  @NotEmpty(message = "Email is required")
  @Email(message = "Invalid email format")
  private String userEmail;

  @NotEmpty(message = "Password is required")
  @PasswordConstraint(message ="Password must contain at least 8 characters including one uppercase letter one lowercase letter one number and one special character")
  private String password;
  
  public UserRegistration()
  {
	  
  }

public UserRegistration(String firstName, String lastName, String userEmail, String password) {
	super();
	this.firstName = firstName;
	this.lastName = lastName;
	this.userEmail = userEmail;
	this.password = password;
}

public Long getUserId() {
	return userId;
}

public void setUserId(Long userId) {
	this.userId = userId;
}

public String getFirstName() {
	return firstName;
}

public void setFirstName(String firstName) {
	this.firstName = firstName;
}

public String getLastName() {
	return lastName;
}

public void setLastName(String lastName) {
	this.lastName = lastName;
}

public String getUserEmail() {
	return userEmail;
}

public void setUserEmail(String userEmail) {
	this.userEmail = userEmail;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}








}

