package com.diagrammingtool.app.service;

import java.util.List;

import com.diagrammingtool.app.model.UserRegistration;

public interface UserRegistrationService {
     public UserRegistration CreateNewUser(UserRegistration user);
     public List<UserRegistration> getAllUser();
}
