package com.chernomurov.effectivemobile.test.task.management.system.exception;

public class UnauthorizedCustomerTaskAccessException extends RuntimeException{
    public UnauthorizedCustomerTaskAccessException(String message) {
        super(message);
    }
}
