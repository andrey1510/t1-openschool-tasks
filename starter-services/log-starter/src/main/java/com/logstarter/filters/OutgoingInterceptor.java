package com.logstarter.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

//@Component
@Slf4j
public class OutgoingInterceptor implements ClientHttpRequestInterceptor {

    //private static final Logger logger = LoggerFactory.getLogger(OutgoingInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        long start = System.currentTimeMillis();
        ClientHttpResponse response = execution.execute(request, body);
        long duration = System.currentTimeMillis() - start;

        log.info("Outgoing request: {} {}", request.getMethod(), request.getURI().toURL());
        log.info("Headers of outgoing request : {}", request.getHeaders());

        log.info("Code of response to outgoing request : {}", response.getStatusCode());
        log.info("Headers of response to outgoing request : {}", response.getHeaders());
        log.info("Duration of outgoing request : {}ms", duration);

        return response;
    }

}