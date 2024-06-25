package com.logstarter.configs;

import com.logstarter.filters.IngoingFilter;
import com.logstarter.filters.OutgoingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogAutoConfiguration {

    @Bean
    public IngoingFilter incomingFilter() {
        return new IngoingFilter();
    }

    @Bean
    public OutgoingInterceptor outgoingInterceptor() {
        return new OutgoingInterceptor();
    }


}
