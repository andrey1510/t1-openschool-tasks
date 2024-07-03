package com.securityjwt.exceptions;

public class RefreshFailedException extends RuntimeException {
    public RefreshFailedException(String message) {
        super(message);
    }
}
