package com.timetracker.exceptions;

public class WrongNumberException extends RuntimeException {
    public WrongNumberException(String message) {
        super(message);
    }
}
