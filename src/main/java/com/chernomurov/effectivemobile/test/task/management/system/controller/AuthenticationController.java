package com.chernomurov.effectivemobile.test.task.management.system.controller;

import com.chernomurov.effectivemobile.test.task.management.system.custom.dto.AuthenticateUserRequest;
import com.chernomurov.effectivemobile.test.task.management.system.custom.dto.RegisterUserRequest;
import com.chernomurov.effectivemobile.test.task.management.system.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/authentication")
@PreAuthorize("permitAll()")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(@Valid @RequestBody RegisterUserRequest request) {
        return ResponseEntity.ok(authenticationService.registerUser(request));
    }

    @GetMapping("/authenticate")
    public ResponseEntity<Map<String, Object>> authenticateUser(@Valid @RequestBody AuthenticateUserRequest request) {
        return ResponseEntity.ok(authenticationService.authenticateUser(request));
    }
}
