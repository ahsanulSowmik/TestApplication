package com.bracait.test.exceptions;

public class InvalidBatteryException extends RuntimeException {
    public InvalidBatteryException(String message) {
        super(message);
    }
}