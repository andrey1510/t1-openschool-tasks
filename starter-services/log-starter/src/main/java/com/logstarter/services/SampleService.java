package com.logstarter.services;

import org.springframework.http.ResponseEntity;

public interface SampleService {

    String requestAndReceiveResponse();

    String requestAndReceiveError(boolean b);

    ResponseEntity<String> requestOtherAppAndReceiveResponse(String url);

    ResponseEntity<String> requestOtherAppAndReceiveError(String url);

}
