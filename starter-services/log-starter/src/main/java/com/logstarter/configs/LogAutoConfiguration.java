package com.logstarter.configs;

import com.logstarter.filters.IngoingFilter;
import com.logstarter.filters.OutgoingInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogAutoConfiguration {

    @ConditionalOnExpression("${logger.ingoing.enabled:true}")
    @Bean
    public IngoingFilter ingoingFilter(){
        return new IngoingFilter();
    }

    @Bean
    public OutgoingInterceptor outgoingInterceptor() {
        return new OutgoingInterceptor();
    }

}
