package com.timetracker.services.sampleOne;

import com.timetracker.annotations.TrackAsyncTime;
import com.timetracker.annotations.TrackTime;
import com.timetracker.utils.ThreadUtils;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class SampleServiceOneImpl implements SampleServiceOne {

    @TrackTime
    @Override
    public String fixedtimeMethodTimetrack(int duration){
        //Simulating synchronous operation with short duration.
        ThreadUtils.waitTime(duration);
        return "Async output";
    }

    @TrackAsyncTime
    @Override
    public String fixedtimeMethodTrackAsync(int duration){
        // Simulating some asynchronous operation with long duration.
        return CompletableFuture.supplyAsync(() -> longAsyncOperation(duration)).join();
    }

    private String longAsyncOperation(int duration) {
        ThreadUtils.waitTime(duration);
        return "Async output";
    }
}
