package com.chernomurov.effectivemobile.test.task.management.system.custom.exception;

public class ContractorNotFoundException extends RuntimeException {
    public ContractorNotFoundException(String message) {
        super(message);
    }
}
