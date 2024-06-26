package com.logstarter.filters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class OutgoingInterceptor implements ClientHttpRequestInterceptor {

    private final String OUTGOING_REQUEST_METHOD = "Outgoing request method";
    private final String OUTGOING_REQUEST_URL = "Outgoing request URL";
    private final String OUTGOING_REQUEST_HEADERS = "Outgoing request headers";
    private final String OUTGOING_REQUEST_RESPONSE_CODE = "Code of response to outgoing request";
    private final String OUTGOING_REQUEST_RESPONSE_HEADERS = "Headers of response to outgoing request";
    private final String OUTGOING_REQUEST_DURATION = "Outgoing request duration, ms.";

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        ClientHttpResponse response;
        long start = System.currentTimeMillis();
        String responseHeaders;
        String responseCode;

        try {
            response = execution.execute(request, body);
        } catch (Exception e) {
            responseCode = response.getStatusCode().toString();
            responseHeaders = response.getHeaders().toString();
        } finally {


            Map<String, String> logMessages = new HashMap<>();
            long duration = System.currentTimeMillis() - start;
            logMessages.put(OUTGOING_REQUEST_METHOD, request.getMethod().toString());
            logMessages.put(OUTGOING_REQUEST_URL, request.getURI().toString());
            logMessages.put(OUTGOING_REQUEST_HEADERS, request.getHeaders().toString());
            logMessages.put(OUTGOING_REQUEST_RESPONSE_CODE, responseCode);
            logMessages.put(OUTGOING_REQUEST_RESPONSE_HEADERS, responseHeaders);
            logMessages.put(OUTGOING_REQUEST_DURATION, String.valueOf(duration));

            log.info(logMessages.toString());
        }
            return response;

    }
}