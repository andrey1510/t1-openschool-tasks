package com.logstarter.controllers;

import com.logstarter.services.SampleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/samples")
@RequiredArgsConstructor
@Schema(description = "Все операции функционала логирования.")
public class SampleController {

    private final SampleService sampleService;

    @GetMapping("/request-and-receive-response")
    @Operation(description = "Послать запрос на данный сервис и получить корректный ответ.")
    public ResponseEntity<String> requestAndReceiveResponse() {
        return ResponseEntity.ok(sampleService.requestAndReceiveResponse());
    }

    @GetMapping("/request-and-receive-error")
    @Operation(description = "Послать запрос на данный сервис и получить исключение.")
    public ResponseEntity<String> requestAndReceiveError() {
        return ResponseEntity.ok(sampleService.requestAndReceiveError(true));
    }

    @GetMapping("/request-other-app")
    @Operation(description = "Послать запрос на другой сервис (product-app) и получить корректный ответ.")
    public ResponseEntity<String> requestOtherAppAndReceiveResponse(){
        String url = "http://localhost:8559/samples/receive-request-and-response";
        return sampleService.requestOtherAppAndReceiveResponse(url);
    }

    @GetMapping("/request-other-app-and-receive-error")
    @Operation(description = "Послать запрос на другой сервис (product-app) и получить исключение.")
    public ResponseEntity<String> requestOtherAppAndReceiveError(){
        String url = "http://localhost:8559/samples/receive-request-and-response-with-error";
        return sampleService.requestOtherAppAndReceiveError(url);
    }
}
