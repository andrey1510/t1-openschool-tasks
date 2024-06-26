package com.logstarter.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class IngoingFilter extends OncePerRequestFilter {

    private final String INGOING_REQUEST_METHOD = "Ingoing request method";
    private final String INGOING_REQUEST_URL = "Ingoing request URL";
    private final String INGOING_REQUEST_HEADERS = "Ingoing request headers";
    private final String INGOING_REQUEST_RESPONSE_CODE = "Code of response to ingoing request";
    private final String INGOING_REQUEST_RESPONSE_HEADERS = "Headers of response to ingoing request";
    private final String INGOING_REQUEST_DURATION = "Ingoing request duration, ms.";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        try {
            filterChain.doFilter(request, response);
        } finally {
            stopWatch.stop();

            Map<String, String> logMessages = createLogMap(request, response, stopWatch);

            StringBuilder logMessageBuilder = new StringBuilder();
            logMessages.forEach((key, value) -> logMessageBuilder.append(key).append(": ").append(value).append("\n"));
            log.info("\n" + logMessageBuilder);
        }
    }

    private Map<String, String> createLogMap(
        HttpServletRequest request, HttpServletResponse response, StopWatch stopWatch) {

        String requestHeaders = Collections.list(request.getHeaderNames()).stream()
            .map(headerName -> headerName + ": " + request.getHeader(headerName))
            .collect(Collectors.joining("; "));
        String responseHeaders = response.getHeaderNames().stream()
            .map(headerName -> headerName + ": " + response.getHeader(headerName))
            .collect(Collectors.joining("; "));

        Map<String, String> logMessages = new HashMap<>();
        logMessages.put(INGOING_REQUEST_METHOD, request.getMethod());
        logMessages.put(INGOING_REQUEST_URL, request.getRequestURL().toString());
        logMessages.put(INGOING_REQUEST_HEADERS, requestHeaders);
        logMessages.put(INGOING_REQUEST_RESPONSE_CODE, String.valueOf(response.getStatus()));
        logMessages.put(INGOING_REQUEST_RESPONSE_HEADERS, responseHeaders);
        logMessages.put(INGOING_REQUEST_DURATION, String.valueOf(stopWatch.getTotalTimeMillis()));
        return logMessages;
    }

}


