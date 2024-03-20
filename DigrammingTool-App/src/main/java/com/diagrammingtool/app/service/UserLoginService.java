package com.diagrammingtool.app.service;


import com.diagrammingtool.app.model.UserRegistration;

public interface UserLoginService {
UserRegistration loginUser(String userEmail, String password);

}
