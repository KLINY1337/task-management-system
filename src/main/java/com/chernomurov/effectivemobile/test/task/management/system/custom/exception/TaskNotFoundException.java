package com.chernomurov.effectivemobile.test.task.management.system.custom.exception;

public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(String message) {
        super(message);
    }
}
