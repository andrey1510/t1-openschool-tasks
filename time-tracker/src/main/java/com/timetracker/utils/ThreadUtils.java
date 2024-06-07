package com.timetracker.utils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ThreadUtils {
    public static void waitTime(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}