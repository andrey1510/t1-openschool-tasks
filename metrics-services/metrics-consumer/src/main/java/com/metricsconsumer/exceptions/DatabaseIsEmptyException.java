package com.metricsconsumer.exceptions;

public class DatabaseIsEmptyException extends RuntimeException {
    public DatabaseIsEmptyException(String message) {
        super(message);
    }
}
