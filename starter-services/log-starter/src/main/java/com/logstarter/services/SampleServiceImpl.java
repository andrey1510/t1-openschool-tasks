package com.logstarter.services;

import com.logstarter.exceptions.IntentionallyCausedException;
import com.logstarter.filters.OutgoingInterceptor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
@Getter
public class SampleServiceImpl implements SampleService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final OutgoingInterceptor outgoingInterceptor = new OutgoingInterceptor();

    @Override
    public String requestAndReceiveResponse() {
        return "Response message from log-starter";
    }

    @Override
    public String requestAndReceiveError(boolean b) {
        if (b) throw new IntentionallyCausedException("log-starter encountered an exception");
        return "void message";
    }

    @Override
    public ResponseEntity<String> requestOtherAppAndReceiveResponse(String url) {
        restTemplate.setInterceptors(Collections.singletonList(outgoingInterceptor));
        return restTemplate.getForEntity(url, String.class);
    }

    @Override
    public ResponseEntity<String> requestOtherAppAndReceiveError(String url) {
        restTemplate.setInterceptors(Collections.singletonList(outgoingInterceptor));
        return restTemplate.getForEntity(url, String.class);
    }
}
