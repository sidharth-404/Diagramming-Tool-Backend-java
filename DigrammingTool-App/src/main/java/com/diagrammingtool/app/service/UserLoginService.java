package com.diagrammingtool.app.service;

import com.diagrammingtool.app.model.UserLogin;

public interface UserLoginService {
	UserLogin loginUser(String userEmail, String password);

}
