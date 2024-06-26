package com.productapp.controllers;

import com.productapp.services.OtherAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/samples")
@RequiredArgsConstructor
public class OtherAppController {

    private final OtherAppService otherAppService;

    @GetMapping("/receive-request-and-response")
    public ResponseEntity<String> receiveAndResponse(){
        return ResponseEntity.ok(otherAppService.receiveAndResponse());
    }

    @GetMapping("/receive-request-and-response-with-error")
    public ResponseEntity<String> receiveAndResponseWithError(){
        return ResponseEntity.ok(otherAppService.receiveAndResponseWithError(true));
    }
}
