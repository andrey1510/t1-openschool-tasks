package com.timetracker.exceptions;

public class DatabaseIsEmptyException extends RuntimeException {
    public DatabaseIsEmptyException(String message) {
        super(message);
    }
}
