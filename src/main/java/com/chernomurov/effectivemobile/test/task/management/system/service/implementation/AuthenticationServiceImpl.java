package com.chernomurov.effectivemobile.test.task.management.system.service.implementation;

import com.chernomurov.effectivemobile.test.task.management.system.entity.*;
import com.chernomurov.effectivemobile.test.task.management.system.repository.*;
import com.chernomurov.effectivemobile.test.task.management.system.custom.dto.AuthenticateUserRequest;
import com.chernomurov.effectivemobile.test.task.management.system.custom.dto.RegisterUserRequest;
import com.chernomurov.effectivemobile.test.task.management.system.custom.exception.RoleNotFoundException;
import com.chernomurov.effectivemobile.test.task.management.system.custom.exception.UserAlreadyExistsException;
import com.chernomurov.effectivemobile.test.task.management.system.service.AuthenticationService;
import com.chernomurov.effectivemobile.test.task.management.system.service.UserService;
import com.chernomurov.effectivemobile.test.task.management.system.util.JwtUtils;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    private final UserService userService;
    private final TokenRepository tokenRepository;


    @Override
    public Map<String, Object> registerUser(RegisterUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("ERROR -> User is already registered (Username: '" +
                    request.getEmail() +
                    "'); Class: " +
                    this.getClass().getName());
        }

        Set<UserRole> userRoles = new HashSet<>();
        request.getRoleNames().stream()
                .map(roleName -> userRoleRepository.findByName(roleName).orElseThrow(() -> new RoleNotFoundException("ERROR -> Role '" + roleName + "' not found" )))
                .forEach(userRoles::add);
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(userRoles)
                .creationDate(LocalDateTime.now())
                .build();
        userRepository.save(user);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "User " + request.getEmail() + " registered successfully");
        return response;
    }

    @Override
    public Map<String, Object> authenticateUser(AuthenticateUserRequest request) {

        UserDetails userDetails = userService.loadUserByUsername(request.getEmail());
        tokenRepository.RevokeAllValidUserTokens((User) userDetails);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        Token accessToken = JwtUtils.generateAccessToken(userDetails);
        Token refreshToken = JwtUtils.generateRefreshToken(userDetails);
        tokenRepository.saveAll(Set.of(accessToken, refreshToken));

        Map<String, Object> response = new HashMap<>();
        response.put("accessToken", accessToken.getValue());
        response.put("refreshToken", refreshToken.getValue());
        return response;
    }
}
