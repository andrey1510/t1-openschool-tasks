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

            Map<String, String> logMessages = createLogMap(request, start, responseHeaders, responseCode);
//                new HashMap<>();
//            long duration = System.currentTimeMillis() - start;
//            logMessages.put(OUTGOING_REQUEST_METHOD, request.getMethod().toString());
//            logMessages.put(OUTGOING_REQUEST_URL, request.getURI().toString());
//            logMessages.put(OUTGOING_REQUEST_HEADERS, request.getHeaders().toString());
//            logMessages.put(OUTGOING_REQUEST_RESPONSE_CODE, responseCode);
//            logMessages.put(OUTGOING_REQUEST_RESPONSE_HEADERS, responseHeaders);
//            logMessages.put(OUTGOING_REQUEST_DURATION, String.valueOf(duration));

            StringBuilder logMessageBuilder = new StringBuilder();
            logMessages.forEach((key, value) -> logMessageBuilder.append(key).append(": ").append(value).append("\n"));
            log.info("\n" + logMessageBuilder);
        }
            return response;
    }

    private Map<String, String> createLogMap(HttpRequest request, long start, String responseHeaders, String responseCode){
        long duration = System.currentTimeMillis() - start;
        Map<String, String> logMessages = new HashMap<>();
        logMessages.put(OUTGOING_REQUEST_METHOD, request.getMethod().toString());
        logMessages.put(OUTGOING_REQUEST_URL, request.getURI().toString());
        logMessages.put(OUTGOING_REQUEST_HEADERS, request.getHeaders().toString());
        logMessages.put(OUTGOING_REQUEST_RESPONSE_CODE, responseCode);
        logMessages.put(OUTGOING_REQUEST_RESPONSE_HEADERS, responseHeaders);
        logMessages.put(OUTGOING_REQUEST_DURATION, String.valueOf(duration));
        return logMessages;
    }
}