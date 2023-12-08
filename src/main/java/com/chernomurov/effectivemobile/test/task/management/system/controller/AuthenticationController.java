package com.chernomurov.effectivemobile.test.task.management.system.controller;

import com.chernomurov.effectivemobile.test.task.management.system.custom.dto.AuthenticateUserRequest;
import com.chernomurov.effectivemobile.test.task.management.system.custom.dto.RegisterUserRequest;
import com.chernomurov.effectivemobile.test.task.management.system.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "Register new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "400", description = "ERROR -> User is already registered",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "404", description = "ERROR -> Specified role not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class)))
    })
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(@Valid @RequestBody RegisterUserRequest request) {
        return ResponseEntity.ok(authenticationService.registerUser(request));
    }

    @Operation(summary = "Authenticate a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User authenticated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class)))
    })
    @GetMapping("/authenticate")
    public ResponseEntity<Map<String, Object>> authenticateUser(@Valid @RequestBody AuthenticateUserRequest request) {
        return ResponseEntity.ok(authenticationService.authenticateUser(request));
    }
}