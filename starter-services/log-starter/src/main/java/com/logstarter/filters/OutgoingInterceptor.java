package com.logstarter.filters;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Getter
public class OutgoingInterceptor implements ClientHttpRequestInterceptor {

    private static final String OUTGOING_REQUEST_METHOD = "Outgoing request method";
    private static final String OUTGOING_REQUEST_URL = "Outgoing request URL";
    private static final String OUTGOING_REQUEST_HEADERS = "Outgoing request headers";
    private static final String OUTGOING_REQUEST_RESPONSE_CODE = "Code of response to outgoing request";
    private static final String OUTGOING_REQUEST_RESPONSE_HEADERS = "Headers of response to outgoing request";
    private static final String OUTGOING_REQUEST_DURATION = "Outgoing request duration, ms.";

    private final Map<String, String> logMessages = new HashMap<>();

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
        throws IOException {

        ClientHttpResponse response;
        String responseHeaders = null;
        String responseCode = null;
        long start = System.currentTimeMillis();

        try {
            response = execution.execute(request, body);
            responseHeaders = response.getStatusCode().toString();
            responseCode = response.getHeaders().toString();
        } finally {
            createLogMap(request, start, responseHeaders, responseCode);

            StringBuilder logMessageBuilder = new StringBuilder();
            logMessages.forEach((key, value) -> logMessageBuilder.append(key).append(": ").append(value).append("\n"));
            log.info("\n" + logMessageBuilder);
        }
            return response;
    }

    private void createLogMap(HttpRequest request, long start, String responseHeaders, String responseCode){
        long duration = System.currentTimeMillis() - start;
        logMessages.put(OUTGOING_REQUEST_METHOD, request.getMethod().toString());
        logMessages.put(OUTGOING_REQUEST_URL, request.getURI().toString());
        logMessages.put(OUTGOING_REQUEST_HEADERS, request.getHeaders().toString());
        logMessages.put(OUTGOING_REQUEST_RESPONSE_CODE, responseCode);
        logMessages.put(OUTGOING_REQUEST_RESPONSE_HEADERS, responseHeaders);
        logMessages.put(OUTGOING_REQUEST_DURATION, String.valueOf(duration));
    }
}