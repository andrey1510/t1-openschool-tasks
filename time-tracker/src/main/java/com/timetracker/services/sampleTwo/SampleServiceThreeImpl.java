package com.timetracker.services.sampleTwo;

import com.timetracker.annotations.TrackAsyncTime;
import com.timetracker.annotations.TrackTime;
import com.timetracker.exceptions.IntentionallyCausedException;
import com.timetracker.utils.ThreadUtils;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class SampleServiceThreeImpl implements SampleServiceThree {

    @TrackTime
    @Override
    public void crashingMethodTracktime(){
        ThreadUtils.waitTime(1000);
        throw new IntentionallyCausedException("Method crashingMethodTracktime() из класса SampleServiceThreeImpl " +
            "пакета com.timetracker.services.sampleTwo был завершен с исключением.");
    }

    @TrackAsyncTime
    @Override
    public void crashingMethodTrackAsync(){
        ThreadUtils.waitTime(1000);
        throw new IntentionallyCausedException("Method crashingMethodTrackAsync() из класса SampleServiceThreeImpl " +
            "пакета com.timetracker.services.sampleTwo был завершен с исключением.");
    }

    @TrackAsyncTime
    @Override
    public int randomMethodOneTrackAsync(){
        Random random = new Random();
        int duration = 100 + random.nextInt(1901);
        ThreadUtils.waitTime(duration);
        return 1;
    }

    @TrackAsyncTime
    @Override
    public int randomMethodTwoTrackAsync(){
        Random random = new Random();
        int duration = 100 + random.nextInt(1901);
        ThreadUtils.waitTime(duration);
        return 2;
    }
}
