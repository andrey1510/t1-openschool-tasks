package com.timetracker.services.sampleTwo;

import com.timetracker.annotations.TrackTime;
import com.timetracker.utils.ThreadUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Slf4j
@Service
public class SampleServiceTwoImpl implements SampleServiceTwo {

    @TrackTime
    @Override
    public String randomMethodTracktime(){
        //Simulating synchronous operation with short duration.
        Random random = new Random();
        int duration = 100 + random.nextInt(900);
        ThreadUtils.waitTime(duration);
        return "Sync output";
    }

    @TrackTime
    @Override
    public String overloadedMethodTracktime(int input){
        //Simulating synchronous operation with short duration.
        Random random = new Random();
        int duration = 100 + random.nextInt(900);
        ThreadUtils.waitTime(duration);
        return "Sync output";
    }

    @TrackTime
    @Override
    public String overloadedMethodTracktime(int input1, long input2){
        //Simulating synchronous operation with short duration.
        Random random = new Random();
        int duration = 100 + random.nextInt(900);
        ThreadUtils.waitTime(duration);
        return "Sync output";
    }

}
