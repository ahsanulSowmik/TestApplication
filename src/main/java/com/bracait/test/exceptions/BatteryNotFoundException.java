package com.bracait.test.exceptions;

public class BatteryNotFoundException extends RuntimeException {
    public BatteryNotFoundException(String message) {
        super(message);
    }
}


