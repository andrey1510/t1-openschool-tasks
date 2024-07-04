package com.securityjwt.security;

import com.securityjwt.models.RefreshToken;
import com.securityjwt.models.User;
import com.securityjwt.repositories.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class TokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration.token}")
    private Duration tokenExpiration;

    @Value("${jwt.expiration.refresh}")
    private Duration refreshToken;

    public String generateAccessToken(User user) {
        return Jwts.builder()
            .setSubject(user.getUsername()).setIssuedAt(new Date())
            .setExpiration(new Date((System.currentTimeMillis() + tokenExpiration.toMillis())))
            .claim("roles", user.getRoles())
            .claim("id", user.getId())
            .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret)))
            .compact();
    }

    public String generateRefreshToken(User user) {
        Date expirationDate = new Date((System.currentTimeMillis() + refreshToken.toMillis()));

        String token = Jwts.builder()
            .setSubject(user.getUsername())
            .setExpiration(expirationDate)
            .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret)))
            .compact();

        refreshTokenRepository.save(RefreshToken.builder()
            .user(user)
            .tokenValue(token)
            .expirationTime(expirationDate.toInstant())
            .build());

        return token;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret)))
                .build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
     }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret)))
            .build().parseClaimsJws(token).getBody();
    }

    @Transactional
    public String findLatestRefreshTokenByUsername(String username) {
        return refreshTokenRepository.findLatestRefreshTokenByUsername(username).orElseThrow().get(0).getTokenValue();
    }

}