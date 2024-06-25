package com.logstarter.filters;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
//@Component
public class IngoingFilter implements Filter {

    //extends OncePerRequestFilter {

    //

   // private static final Logger logger = LoggerFactory.getLogger(IngoingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        long startTime = System.currentTimeMillis();
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        log.info("Incoming request: {} {}", httpRequest.getMethod(), httpRequest.getRequestURL());
        log.info("Headers of incoming request : {}", httpRequest.getHeaderNames());

        HttpServletResponse httpResponse = (HttpServletResponse) response;
        chain.doFilter(request, response);

        long duration = System.currentTimeMillis() - startTime;

        log.info("Code of response to incoming request: {}", httpResponse.getStatus());
        log.info("Headers of response to incoming request : {}", httpResponse.getHeaderNames());
        log.info("Duration of incoming request : {}ms", duration);
    }
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start();
//
//        try {
//            // Логирование запроса
//            logger.info("Received {} request to {}", request.getMethod(), request.getRequestURL());
//            logger.info("Request headers: {}", request.getHeaderNames());
//
//            filterChain.doFilter(request, response);
//
//            // Логирование ответа
//            logger.info("Response status: {}", response.getStatus());
//        } finally {
//            stopWatch.stop();
//            logger.info("Request processing time: {} ms", stopWatch.getTotalTimeMillis());
//        }
//    }
}