package com.logstarter.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OutgoingInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(OutgoingInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        long start = System.currentTimeMillis();
        ClientHttpResponse response = execution.execute(request, body);
        long duration = System.currentTimeMillis() - start;

        logger.info("Outgoing request: {} {}", request.getMethod(), request.getURI());
        logger.info("Outgoing request Headers: {}", request.getHeaders());

        logger.info("Response to Outgoing request Code: {}", response.getStatusCode());
        logger.info("Response to Outgoing request Headers: {}", response.getHeaders());
        logger.info("Outgoing request Duration: {}ms", duration);

        return response;
    }

}