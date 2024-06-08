package com.timetracker.services.sampleTwo;

import com.timetracker.annotations.TrackAsyncTime;

public interface SampleServiceThree {

    void crashingMethodTracktime();

    void crashingMethodTrackAsync();

    int randomMethodOneTrackAsync();

    int randomMethodTwoTrackAsync();
}
