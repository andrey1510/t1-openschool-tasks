package com.securityjwt.controllers;

import com.securityjwt.enums.Role;
import com.securityjwt.exceptions.AuthenticationFailedException;
import com.securityjwt.exceptions.UserAlreadyExistsException;
import com.securityjwt.exceptions.UserNotFoundException;
import com.securityjwt.models.User;
import com.securityjwt.models.TokenResponse;
import com.securityjwt.repositories.RefreshTokenRepository;
import com.securityjwt.security.TokenService;
import com.securityjwt.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.RefreshFailedException;
import java.util.HashSet;
import java.util.Set;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Schema(description = "Все операции по регистрации и аутентификации пользователя.")
public class UserController {

    private final UserService userService;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository tokenRepository;

    @PostMapping("/register")
    @Operation(description = "Зарегистрировать пользователя. По умолчанию новому пользователю присваивается роль User, " +
        "также можно дополнительно присвоить роли Performer и Customer.")
    public ResponseEntity<String> createUser(
                @Parameter(description = "Задать имя пользователя.")
                @RequestParam String username,
                @Parameter(description = "Задать пароль.")
                @RequestParam String password,
                @Parameter(description = "Присвоить роль Customer.")
                @RequestParam(required = false, defaultValue = "false") boolean customerRole,
                @Parameter(description = "Присвоить роль Performer.")
                @RequestParam(required = false, defaultValue = "false") boolean performerRole) {

        if (userService.findByUsername(username).isPresent()) {
            throw new UserAlreadyExistsException("Пользователь с таким именем уже существует.");
        }

        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        if (customerRole) roles.add(Role.CUSTOMER);
        if (performerRole) roles.add(Role.PERFORMER);

        userService.createUser(User.builder()
            .username(username)
            .password(passwordEncoder.encode(password))
            .roles(roles)
            .build());

        return ResponseEntity.ok("Пользователь успешно зарегистрирован.");
    }

    @PostMapping("/login")
    @Operation(description = "Войти в сервис с логином и паролем.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ok",
            content = {@Content(mediaType = "application/json", array = @ArraySchema(
                schema = @Schema(implementation = TokenResponse.class)))})})
    public ResponseEntity<TokenResponse> login(
        @Parameter(description = "Имя пользователя.")
        @RequestParam String username,
        @Parameter(description = "Пароль.")
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

    @PostMapping("/refresh-token")
    @Operation(description = "Обновить JWT-токен и Refresh-токен.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ok",
            content = {@Content(mediaType = "application/json", array = @ArraySchema(
                schema = @Schema(implementation = TokenResponse.class)))})})
    public ResponseEntity<TokenResponse> refreshToken(@RequestParam String refreshToken) throws RefreshFailedException {

        if (!tokenService.validateToken(refreshToken)) { throw new AuthenticationFailedException("Неверный токен.");}

        User user = userService.findByUsername(tokenService.getClaims(refreshToken).getSubject()).orElseThrow();

        String latestRefreshToken = tokenRepository
            .findLatestRefreshToken(user.getUsername()).orElseThrow().get(0).getTokenValue();

        if (!latestRefreshToken.isEmpty() && latestRefreshToken.equals(refreshToken)) {
            return ResponseEntity.ok(
                new TokenResponse(tokenService.generateAccessToken(user), tokenService.generateRefreshToken(user)));
        } else {
           throw new RefreshFailedException("Не удалось обновить токен");
        }
    }

}
