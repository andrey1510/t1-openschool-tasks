package com.timetracker.services.sampleOne;

import com.timetracker.annotations.TrackAsyncTime;
import com.timetracker.annotations.TrackTime;
import com.timetracker.utils.ThreadUtils;
import org.springframework.stereotype.Service;

@Service
public class SampleServiceOneImpl implements SampleServiceOne {

    @TrackTime
    @Override
    public String fixedtimeMethodTimetrack(int duration){
        ThreadUtils.waitTime(duration);
        return "output";
    }

    @TrackAsyncTime
    @Override
    public String fixedtimeMethodTrackAsync(int duration){
        ThreadUtils.waitTime(duration);
        return "output";
    }
}
