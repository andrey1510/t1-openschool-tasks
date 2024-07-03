package com.securityjwt.configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

//    @Bean
//    public OpenAPI customizeOpenAPI() {
//        final String securitySchemeName = "BearerAuth";
//        return new OpenAPI()
//            .addSecurityItem(new SecurityRequirement()
//                .addList(securitySchemeName))
//            .components(new Components()
//                .addSecuritySchemes(securitySchemeName, new SecurityScheme()
//                    .name(securitySchemeName)
//                    .type(SecurityScheme.Type.HTTP)
//                    .scheme("bearer")
//                    .description(
//                        "Нужно указать JWT токен, полученный при входе через имя пользователя и пароль.")
//                    .bearerFormat("JWT")));
//    }

    @Bean
    public OpenAPI customizeOpenAPI() {
        return new OpenAPI()
            .components(new Components()
                .addSecuritySchemes("BearerAuth", new SecurityScheme()
                    .name("BearerAuth")
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .description(
                        "Нужно указать JWT токен, полученный при входе через имя пользователя и пароль.")
                    .bearerFormat("JWT")));
    }


}
