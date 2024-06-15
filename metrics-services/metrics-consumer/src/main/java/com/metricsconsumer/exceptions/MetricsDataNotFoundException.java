package com.metricsconsumer.exceptions;

public class MetricsDataNotFoundException extends RuntimeException {
    public MetricsDataNotFoundException(String message) {
        super(message);
    }
}
