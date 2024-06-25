package com.productapp.controllers;

import com.productapp.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/help-test-starter")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/receive-and-return-string")
    public ResponseEntity<String> replyToStarter(){

        return ResponseEntity.ok(productService.receiveString());
    }

}
