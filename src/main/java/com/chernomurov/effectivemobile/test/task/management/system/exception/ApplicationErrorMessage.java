package com.chernomurov.effectivemobile.test.task.management.system.exception;

public record ApplicationErrorMessage(long timestamp, int statusCode, Object message) {}
