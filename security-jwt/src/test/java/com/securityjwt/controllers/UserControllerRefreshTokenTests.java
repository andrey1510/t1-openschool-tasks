package com.securityjwt.controllers;

import com.securityjwt.enums.Role;
import com.securityjwt.exceptions.AuthenticationFailedException;
import com.securityjwt.models.TokenResponse;
import com.securityjwt.models.User;
import com.securityjwt.security.TokenService;
import com.securityjwt.services.UserService;
import lombok.SneakyThrows;
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
import io.jsonwebtoken.Jwts;

@ExtendWith(MockitoExtension.class)
public class UserControllerRefreshTokenTests {

    @Mock
    private UserService userService;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private UserController userController;

    private final User user = User.builder()
        .username("sampleUsername")
        .roles(Collections.singleton(Role.USER))
        .build();

    @Test
    @SneakyThrows
    public void refreshTokenWithSuccess() {
        String accessToken = "accessToken";
        String refreshToken = "refreshToken";

        when(tokenService.validateToken(refreshToken)).thenReturn(true);
        when(tokenService.getClaims(refreshToken)).thenReturn(Jwts.claims().setSubject("sampleUsername"));
        when(userService.findByUsername("sampleUsername")).thenReturn(Optional.of(user));
        when(tokenService.findLatestRefreshTokenByUsername("sampleUsername")).thenReturn(refreshToken);
        when(tokenService.generateAccessToken(user)).thenReturn(accessToken);
        when(tokenService.generateRefreshToken(user)).thenReturn(refreshToken);

        ResponseEntity<TokenResponse> response = userController.refreshToken(refreshToken);

        assertEquals(accessToken, response.getBody().getToken());
        assertEquals(refreshToken, response.getBody().getRefreshToken());
    }

    @Test
    public void testRefreshTokenWithInvalidToken() {
        String invalidToken = "wrongRefreshToken";

        when(tokenService.validateToken(invalidToken)).thenReturn(false);

        assertThrows(AuthenticationFailedException.class, () -> {
            userController.refreshToken(invalidToken);
        });
    }
}
