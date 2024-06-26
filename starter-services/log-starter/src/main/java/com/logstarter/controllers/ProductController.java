package com.logstarter.controllers;

import com.logstarter.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test-starter")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/send-and-receive-string")
    public ResponseEntity<String> receiveString(
        @RequestParam(value = "send input string", required = false) String str) {
        return ResponseEntity.ok(productService.receiveString(str));
    }

    @GetMapping("/request-and-receive")
    public ResponseEntity<String> requestAndReceive(){
        String url = "http://localhost:8559/help-test-starter/receive-and-return-string";
        return productService.requestAndReceive(url);
    }


    @GetMapping("/receive-info/{id}")
    public ResponseEntity<String> getUserById(@PathVariable Long id) {
        String url = "https://jsonplaceholder.typicode.com/users/" + id;
        return productService.getUserById(url);
    }

}
