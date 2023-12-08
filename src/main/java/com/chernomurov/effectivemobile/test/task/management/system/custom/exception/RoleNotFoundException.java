package com.chernomurov.effectivemobile.test.task.management.system.custom.exception;

public class RoleNotFoundException extends RuntimeException{
    public RoleNotFoundException(String message) {
        super(message);
    }
}
