package com.chernomurov.effectivemobile.test.task.management.system.service;

import com.chernomurov.effectivemobile.test.task.management.system.custom.dto.AuthenticateUserRequest;
import com.chernomurov.effectivemobile.test.task.management.system.custom.dto.RegisterUserRequest;

import java.util.Map;

public interface AuthenticationService {
    Map<String, Object> registerUser(RegisterUserRequest request);
    Map<String, Object> authenticateUser(AuthenticateUserRequest request);
}
