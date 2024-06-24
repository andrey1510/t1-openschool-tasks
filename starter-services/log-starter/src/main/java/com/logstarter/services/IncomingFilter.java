package com.logstarter.services;

import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class IncomingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(IncomingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        long startTime = System.currentTimeMillis();
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        logger.info("Incoming request: {} {}", httpRequest.getMethod(), httpRequest.getRequestURL());
        logger.info("Incoming request Headers: {}", httpRequest.getHeaderNames());

        HttpServletResponse httpResponse = (HttpServletResponse) response;
        chain.doFilter(request, response);

        long duration = System.currentTimeMillis() - startTime;

        logger.info("Response to Incoming request Code: {}", httpResponse.getStatus());
        logger.info("Response to Incoming request Headers: {}", httpResponse.getHeaderNames());
        logger.info("Incoming request Duration: {}ms", duration);
    }

}