package com.logstarter.services;

import com.logstarter.exceptions.IntentionallyCausedException;
import com.logstarter.filters.OutgoingInterceptor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Collections;
import java.util.Objects;

@Service
public class ProductServiceImpl implements ProductService {

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public String receiveString(String str) {


        if (Objects.equals(str, "11")) {
            throw new IntentionallyCausedException("exxxxxxxxxxx");
        }

        if (Objects.equals(str, "send input string")) {
            return "received output string";
        } else {
            throw new RuntimeException("Intentional error");
        }

    }

    @Override
    public ResponseEntity<String> requestAndReceive(String url){
        restTemplate.setInterceptors(Collections.singletonList(new OutgoingInterceptor()));
        return restTemplate.getForEntity(url, String.class);

    }

    @Override
    public ResponseEntity<String> getUserById(String url){
        restTemplate.setInterceptors(Collections.singletonList(new OutgoingInterceptor()));
        return restTemplate.getForEntity(url, String.class);
    }


    @Override
    @Scheduled(fixedRate = 5000)
    public ResponseEntity<String> sendAuto(){
        String url = "http://localhost:8559/help-test-starter/receive-and-return-string";
        restTemplate.setInterceptors(Collections.singletonList(new OutgoingInterceptor()));

        ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
        System.out.println(forEntity);
        return forEntity;
    }
}
