package com.chernomurov.effectivemobile.test.task.management.system.custom.exception;

public record ApplicationErrorMessage(long timestamp, int statusCode, Object message) {}
