package com.securityjwt.controllers;

import com.securityjwt.enums.Role;
import com.securityjwt.exceptions.AuthenticationFailedException;
import com.securityjwt.exceptions.UserAlreadyExistsException;
import com.securityjwt.exceptions.UserNotFoundException;
import com.securityjwt.models.User;
import com.securityjwt.models.dto.TokenResponse;
import com.securityjwt.security.TokenService;
import com.securityjwt.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController { //ToDo

    private final UserService userService;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(
                @RequestParam String username,
                @RequestParam String password) {

        User user = userService.findByUsername(username)
            .orElseThrow(() -> new UserNotFoundException("Пользователь с таким именем не найден"));

        if (passwordEncoder.matches(password, user.getPassword())) {
            final String token = tokenService.generateAccessToken(user);
            final String refreshToken = tokenService.generateRefreshToken(user);
            return  ResponseEntity.ok(new TokenResponse(token, refreshToken));
        } else {
            throw new AuthenticationFailedException("Указан неверный пароль.");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> createUser(
                @RequestParam String username,
                @RequestParam String password,
                @RequestParam(required = false, defaultValue = "false") boolean customerRole,
                @RequestParam(required = false, defaultValue = "false") boolean performerRole) {

        if (userService.findByUsername(username).isPresent()) {
            throw new UserAlreadyExistsException("Пользователь с таким именем уже существует.");
        }

        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        if (customerRole) roles.add(Role.PERFORMER);
        if (performerRole) roles.add(Role.CUSTOMER);

        userService.createUser(User.builder()
            .username(username)
            .password(passwordEncoder.encode(password))
            .roles(roles)
            .build());

        return ResponseEntity.ok("Пользователь успешно зарегистрирован.");
    }

}
