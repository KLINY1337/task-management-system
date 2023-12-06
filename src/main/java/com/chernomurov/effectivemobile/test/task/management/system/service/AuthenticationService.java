package com.chernomurov.effectivemobile.test.task.management.system.service;

import com.chernomurov.effectivemobile.test.task.management.system.request.AuthenticateUserRequest;
import com.chernomurov.effectivemobile.test.task.management.system.request.RegisterUserRequest;

import java.util.Map;

public interface AuthenticationService {
    Map<String, Object> registerUser(RegisterUserRequest request);
    Map<String, Object> authenticateUser(AuthenticateUserRequest request);
}
