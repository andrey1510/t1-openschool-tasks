package com.securityjwt.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "JWT-токен и refresh-токен, выдаваемые пользователю, " +
    "вошедшему в сервис по имени пользователя и паролю.")
public class TokenResponse {

    @Schema(description = "JWT-токен")
    private String token;

    @Schema(description = "refresh-токен")
    private String refreshToken;

}
