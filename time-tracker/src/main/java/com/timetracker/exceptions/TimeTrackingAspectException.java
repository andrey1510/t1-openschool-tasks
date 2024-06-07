package com.timetracker.exceptions;

public class TimeTrackingAspectException extends RuntimeException {
    public TimeTrackingAspectException(String message, Throwable cause) {
        super(message, cause);
    }
}
