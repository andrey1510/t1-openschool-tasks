package com.timetracker.services;

import com.timetracker.annotations.TrackAsyncTime;
import com.timetracker.utils.ThreadUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@EnableAsync
public class AsyncSampleServiceImpl implements AsyncSampleService {

    @TrackAsyncTime
    @Async
    @Override
    public void asyncFixedtimeMethod(int duration) {
        CompletableFuture.runAsync(() -> ThreadUtils.waitTime(duration));
    }

    @TrackAsyncTime
   // @Async
    @Override
    public CompletableFuture<String> asyncFirstRandomtimeMethod(){
        Random random = new Random();
        int duration = 20000 + random.nextInt(1000);
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(duration);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException(e);
            }
        }).thenApply(v -> "output");
        return stringCompletableFuture;
    }

    @TrackAsyncTime
   // @Async
    @Override
    public CompletableFuture<String> asyncSecondRandomtimeMethod(){
        Random random = new Random();
        int duration = 2000 + random.nextInt(1000);
        return CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(duration);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException(e);
            }
        }).thenApply(v -> "output");
    }

    @TrackAsyncTime
   // @Async
    @Override
    public CompletableFuture<String> asyncThirdRandomtimeMethod(){

        Random random = new Random();
        int duration = 2000 + random.nextInt(1000);
        return CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(duration);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalStateException(e);
            }
        }).thenApply(v -> "output");
    }

}
