package com.metricsconsumer.exceptions;

public class MetricsTypeNotFoundException extends RuntimeException {
    public MetricsTypeNotFoundException(String message) {
        super(message);
    }
}
