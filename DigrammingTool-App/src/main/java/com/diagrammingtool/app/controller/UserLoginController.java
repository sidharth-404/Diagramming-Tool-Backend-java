package com.diagrammingtool.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.diagrammingtool.app.model.UserLogin;
import com.diagrammingtool.app.service.UserLoginService;

public class UserLoginController {
	@Autowired
    private UserLoginService userLoginService;

    @PostMapping("/login")
    public UserLogin loginUser(@RequestBody UserLogin userLogin) {
        return userLoginService.loginUser(userLogin.getUserEmail(), userLogin.getPassword());
    }

}
