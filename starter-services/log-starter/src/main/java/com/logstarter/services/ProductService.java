package com.logstarter.services;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;

public interface ProductService {

    String receiveString(String str);

    ResponseEntity<String> requestAndReceive(String url);

    ResponseEntity<String> getUserById(String url);

    @Scheduled(fixedRate = 100)
    ResponseEntity<String> sendAuto();
}
