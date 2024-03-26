package com.diagrammingtool.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.diagrammingtool.app.model.UserRegistration;
import com.diagrammingtool.app.repository.UserRegistrationRepository;


@SpringBootTest
public class UserLoginServiceImplTest {

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

    @Test
    public void testLoginUser_UserNotFound() {
        when(userDetails.findByUserEmail(any(String.class))).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> {
            userLoginService.loginUser("test@example.com", "password");
        });
    }

    @Test
    public void testLoginUser_InvalidPassword() {
        UserRegistration user = new UserRegistration();
        user.setUserEmail("test@example.com");
        user.setPassword("password");

        when(userDetails.findByUserEmail(any(String.class))).thenReturn(user);
        when(passwordEncrypt.matches(any(String.class), any(String.class))).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> {
            userLoginService.loginUser("test@example.com", "invalid_password");
        });
    }
}
