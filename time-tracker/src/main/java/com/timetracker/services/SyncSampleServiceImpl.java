package com.timetracker.services;

import com.timetracker.annotations.TrackTime;
import com.timetracker.utils.ThreadUtils;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class SyncSampleServiceImpl implements SyncSampleService {

    @TrackTime
    @Override
    public String syncFixedtimeMethod(int duration){
        ThreadUtils.waitTime(duration);
        return "output";
    }

    @TrackTime
    @Override
    public boolean syncSimpleMethod(){
        Random random = new Random();
        int duration = 100 + random.nextInt(1901);
        ThreadUtils.waitTime(duration);
        return true;
    }

    @TrackTime
    @Override
    public int syncOverloadedMethod(int input){
        Random random = new Random();
        int duration = 100 + random.nextInt(1901);
        ThreadUtils.waitTime(duration);
        return 1;
    }

    @TrackTime
    @Override
    public int syncOverloadedMethod(int input1, long input2){
        Random random = new Random();
        int duration = 100 + random.nextInt(1901);
        ThreadUtils.waitTime(duration);
        return 2;
    }

    @TrackTime
    @Override
    public void syncCrashingMethod(){
        ThreadUtils.waitTime(1000);
        throw new RuntimeException("This method waited for 1 second and crash.");
    }

}
