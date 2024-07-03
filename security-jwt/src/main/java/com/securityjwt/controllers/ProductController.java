package com.securityjwt.controllers;

import com.securityjwt.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/shop/perforer-board/check-access")
    public ResponseEntity<String> checkPerformerAccess(){
        return ResponseEntity.ok(productService.checkPerformerAccess());
    }

    @GetMapping("/shop/customer-board/check-access")
    public ResponseEntity<String> checkCustomerAccess(){
        return ResponseEntity.ok(productService.checkCustomerAccess());
    }

    @GetMapping("/shop/statistics/check-performer-and-customer-access")
    public ResponseEntity<String> checkBothAccess(){
        return ResponseEntity.ok(productService.checkBothAccess());
    }

    @Operation(
        security = @SecurityRequirement(name = "BearerAuth")
    )
    @GetMapping("/shop/demo/check-user-access")
    public ResponseEntity<String> checkUserAccess(){
        return ResponseEntity.ok(productService.checkUserAccess());
    }

}
