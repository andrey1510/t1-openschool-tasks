package com.timetracker.services;


import java.util.concurrent.CompletableFuture;

public interface AsyncSampleService {

    void asyncFixedtimeMethod(int duration);

    CompletableFuture<String> asyncFirstRandomtimeMethod();

    CompletableFuture<String> asyncSecondRandomtimeMethod();

    CompletableFuture<String> asyncThirdRandomtimeMethod();
}
