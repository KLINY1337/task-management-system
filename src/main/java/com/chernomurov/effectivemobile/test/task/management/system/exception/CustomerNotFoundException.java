package com.chernomurov.effectivemobile.test.task.management.system.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
