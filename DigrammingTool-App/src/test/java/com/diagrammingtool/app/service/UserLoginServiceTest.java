package com.diagrammingtool.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.diagrammingtool.app.model.UserRegistration;
import com.diagrammingtool.app.repository.UserRegistrationRepository;


@SpringBootTest
public class UserLoginServiceTest {

    @Mock
    private UserRegistrationRepository userDetails;

    @Mock
    private PasswordEncoder passwordEncrypt;

    @InjectMocks
    private UserLoginServiceImpl userLoginService;

    @Test
    public void testLoginUser_Success() {
        UserRegistration user = new UserRegistration();
        user.setUserEmail("test@example.com");
        user.setPassword("password");

        when(userDetails.findByUserEmail(any(String.class))).thenReturn(user);
        when(passwordEncrypt.matches(any(String.class), any(String.class))).thenReturn(true);

      userLoginService.loginUser("test@example.com", "password");

      verify(userDetails, times(1)).findByUserEmail("test@example.com");
    }
}
