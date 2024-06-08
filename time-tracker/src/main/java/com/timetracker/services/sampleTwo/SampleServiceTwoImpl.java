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
    public boolean randomMethodTracktime(){
        Random random = new Random();
        int duration = 100 + random.nextInt(1901);
        ThreadUtils.waitTime(duration);
        return true;
    }

    @TrackTime
    @Override
    public int overloadedMethodTracktime(int input){
        Random random = new Random();
        int duration = 100 + random.nextInt(1901);
        ThreadUtils.waitTime(duration);
        return 1;
    }

    @TrackTime
    @Override
    public int overloadedMethodTracktime(int input1, long input2){
        Random random = new Random();
        int duration = 100 + random.nextInt(1901);
        ThreadUtils.waitTime(duration);
        return 2;
    }

}
