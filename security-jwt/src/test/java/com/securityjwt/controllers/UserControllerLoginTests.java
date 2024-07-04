package com.securityjwt.controllers;

import com.securityjwt.enums.Role;
import com.securityjwt.exceptions.AuthenticationFailedException;
import com.securityjwt.models.TokenResponse;
import com.securityjwt.models.User;
import com.securityjwt.security.TokenService;
import com.securityjwt.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import java.util.Collections;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserControllerLoginTests {

    @Mock
    private UserService userService;

    @Mock
    private TokenService tokenService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserController userController;

    private final User user = User.builder()
        .username("sampleUsername")
        .password("encodedPassword")
        .roles(Collections.singleton(Role.USER))
        .build();

    @Test
    public void loginWithSuccess() {

        String accessToken = "accessToken";
        String refreshToken = "refreshToken";

        when(userService.findByUsername("sampleUsername")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("correctPassword", user.getPassword())).thenReturn(true);
        when(tokenService.generateAccessToken(user)).thenReturn(accessToken);
        when(tokenService.generateRefreshToken(user)).thenReturn(refreshToken);

        ResponseEntity<TokenResponse> response = userController.login("sampleUsername", "correctPassword");

        assertEquals(accessToken, response.getBody().getToken());
        assertEquals(refreshToken, response.getBody().getRefreshToken());
    }

    @Test
    public void loginWithWrongPassword() {

        when(userService.findByUsername("sampleUsername")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongPassword", user.getPassword())).thenReturn(false);

        assertThrows(AuthenticationFailedException.class, () -> {
            userController.login("sampleUsername", "wrongPassword");
        });
    }
}