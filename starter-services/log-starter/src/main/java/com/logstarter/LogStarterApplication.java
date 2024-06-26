package com.logstarter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class LogStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogStarterApplication.class, args);
    }

}
