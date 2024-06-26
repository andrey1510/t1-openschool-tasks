package com.logstarter.configs;

import com.logstarter.filters.IngoingFilter;
import com.logstarter.filters.OutgoingInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogAutoConfiguration {


    @Bean
    @ConditionalOnExpression("${logger.outgoing.enabled:false}")
    public OutgoingInterceptor outgoingInterceptor() {
        return new OutgoingInterceptor();
    }

    @ConditionalOnExpression("${logger.ingoing.enabled:false}")
    @Bean
    public IngoingFilter ingoingFilter(){
        return new IngoingFilter();
    }

}
