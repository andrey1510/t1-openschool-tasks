package com.securityjwt.controllers;

import com.securityjwt.services.SampleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Schema(description = "Все операции с примерами авторизации пользователей.")
public class SampleController {

    private final SampleService sampleService;

    @Operation(
        description = "К этому методу могут иметь доступ только зарегистрированные пользователи с ролью Performer.",
        security = {@SecurityRequirement(name = "BearerAuth")})
    @GetMapping("/shop/performer-board/check-access")
    public ResponseEntity<String> checkPerformerAccess(){
        return ResponseEntity.ok(sampleService.checkPerformerAccess());
    }

    @Operation(
        description = "К этому методу могут иметь доступ только зарегистрированные пользователи с ролью Customer.",
        security = {@SecurityRequirement(name = "BearerAuth")} )
    @GetMapping("/shop/customer-board/check-access")
    public ResponseEntity<String> checkCustomerAccess(){
        return ResponseEntity.ok(sampleService.checkCustomerAccess());
    }

    @Operation(
        description = "К этому методу могут иметь доступ только зарегистрированные пользователи с ролью Performer или Customer.",
        security = {@SecurityRequirement(name = "BearerAuth")})
    @GetMapping("/shop/statistics/check-performer-and-customer-access")
    public ResponseEntity<String> checkBothAccess(){
        return ResponseEntity.ok(sampleService.checkBothAccess());
    }

    @Operation(
        description = "К этому методу могут иметь доступ все зарегистрированные пользователи.",
        security = {@SecurityRequirement(name = "BearerAuth")})
    @GetMapping("/shop/demo/check-user-access")
    public ResponseEntity<String> checkUserAccess(){
        return ResponseEntity.ok(sampleService.checkUserAccess());
    }

}
