package com.productapp.controllers;

import com.productapp.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/contr")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    private final RestTemplate restTemplate = new RestTemplate();


    @GetMapping("/hi")
    public ResponseEntity<String> receiveString(@RequestParam(value = "hi", required = false) String hi){
        return ResponseEntity.ok(productService.receiveString(hi));
    }


    @GetMapping("/users/{id}")
    public ResponseEntity<String> getUserById(@PathVariable Long id) {
        String url = "https://jsonplaceholder.typicode.com/users/" + id;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response;
    }

}
