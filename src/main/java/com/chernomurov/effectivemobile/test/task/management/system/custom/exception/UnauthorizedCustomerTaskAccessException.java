package com.chernomurov.effectivemobile.test.task.management.system.custom.exception;

public class UnauthorizedCustomerTaskAccessException extends RuntimeException{
    public UnauthorizedCustomerTaskAccessException(String message) {
        super(message);
    }
}
