package com.diagrammingtool.app.model;

public class UserLogin {
	private String userEmail;
	private String password;
	
	public UserLogin() {
		
	}

	public UserLogin(String userEmail, String password) {
		super();
		this.userEmail = userEmail;
		this.password = password;
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
