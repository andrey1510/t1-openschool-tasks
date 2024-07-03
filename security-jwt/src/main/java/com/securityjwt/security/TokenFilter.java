package com.securityjwt.security;

import com.securityjwt.enums.Role;
import com.securityjwt.models.TokenAuthentication;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TokenFilter extends GenericFilterBean {

    private final TokenService tokenService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
        throws IOException, ServletException {

        String bearer = ((HttpServletRequest) request).getHeader("Authorization");
        String token = null;
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            token = bearer.substring(7);
        }

        if (token != null && tokenService.validateAccessToken(token)) {
            Claims claims = tokenService.getAccessClaims(token);
            TokenAuthentication tokenAuth = TokenAuthentication.builder()
                .username(claims.getSubject())
                .authenticated(true)
                .name(claims.get("name", String.class))
                .roles(((List<String>) claims.get("roles", List.class)).stream()
                    .map(Role::valueOf)
                    .collect(Collectors.toSet()))
                .build();

            SecurityContextHolder.getContext().setAuthentication(tokenAuth);
        }

        filterChain.doFilter(request, response);
    }

}