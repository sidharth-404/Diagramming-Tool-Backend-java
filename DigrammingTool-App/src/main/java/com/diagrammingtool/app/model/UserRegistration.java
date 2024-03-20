package com.diagrammingtool.app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserRegistration {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long userId;
  private String firstName;
  private String lastName;
  private String userEmail;
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

