package com.timetracker.services.sampleTwo;

import com.timetracker.annotations.TrackAsyncTime;
import com.timetracker.annotations.TrackTime;
import com.timetracker.exceptions.IntentionallyCausedException;
import com.timetracker.utils.ThreadUtils;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

@Service
public class SampleServiceThreeImpl implements SampleServiceThree {

    @TrackTime
    @Override
    public void crashingMethodTracktime(){
        //Simulating synchronous operation with short duration.
        ThreadUtils.waitTime(1000);
        throw new IntentionallyCausedException("Method crashingMethodTracktime() из класса SampleServiceThreeImpl " +
            "пакета com.timetracker.services.sampleTwo был завершен с исключением.");
    }

    @TrackAsyncTime
    @Override
    public String randomMethodOneTrackAsync(){
        Random random = new Random();
        int duration = 2000 + random.nextInt(1000);

        //Simulating asynchronous operation with long duration.
        return CompletableFuture.supplyAsync(() -> longAsyncOperation(duration)).join();
    }

    @TrackAsyncTime
    @Override
    public String randomMethodTwoTrackAsync(){
        Random random = new Random();
        int duration = 2000 + random.nextInt(1000);

        //Simulating asynchronous operation with long duration.
        return CompletableFuture.supplyAsync(() -> longAsyncOperation(duration)).join();
    }

    private String longAsyncOperation(int duration) {
        ThreadUtils.waitTime(duration);
        return "Async output";
    }

}
