package com.securityjwt.services;

import com.securityjwt.enums.Role;
import com.securityjwt.models.RefreshToken;
import com.securityjwt.models.User;
import com.securityjwt.repositories.RefreshTokenRepository;
import com.securityjwt.security.TokenService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class TokenServiceTests {

    @Autowired
    private TokenService tokenService;

    @MockBean
    private RefreshTokenRepository refreshTokenRepository;

    private final User user = User.builder()
        .username("sampleUsername")
        .password("samplePassword")
        .roles(Collections.singleton(Role.USER))
        .build();

    @Test
    void generateAccessToken() {
        assertNotNull(tokenService.generateAccessToken(user));
    }

    @Test
    void generateRefreshToken() {
        when(refreshTokenRepository.save(any(RefreshToken.class))).thenReturn(new RefreshToken());

        assertNotNull(tokenService.generateRefreshToken(user));
    }

    @Test
    void validateToken() {
        assertTrue(tokenService.validateToken(tokenService.generateAccessToken(user)));
    }
}