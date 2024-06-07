package com.timetracker.services;

public interface SyncSampleService {

    String syncFixedtimeMethod(int duration);

    boolean syncSimpleMethod();

    int syncOverloadedMethod(int input);

    int syncOverloadedMethod(int input1, long input2);

    void syncCrashingMethod();
}
