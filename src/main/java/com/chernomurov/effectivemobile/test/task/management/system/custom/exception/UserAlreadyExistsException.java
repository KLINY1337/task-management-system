package com.chernomurov.effectivemobile.test.task.management.system.custom.exception;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
