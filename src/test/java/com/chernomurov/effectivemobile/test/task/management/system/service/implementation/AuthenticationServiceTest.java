package com.chernomurov.effectivemobile.test.task.management.system.service.implementation;


import com.chernomurov.effectivemobile.test.task.management.system.custom.dto.RegisterUserRequest;
import com.chernomurov.effectivemobile.test.task.management.system.custom.exception.RoleNotFoundException;
import com.chernomurov.effectivemobile.test.task.management.system.custom.exception.UserAlreadyExistsException;
import com.chernomurov.effectivemobile.test.task.management.system.entity.User;
import com.chernomurov.effectivemobile.test.task.management.system.entity.UserRole;
import com.chernomurov.effectivemobile.test.task.management.system.repository.UserRepository;
import com.chernomurov.effectivemobile.test.task.management.system.repository.UserRoleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserRoleRepository userRoleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Test
    public void testRegisterUser_Success() {
        // Arrange
        RegisterUserRequest request = new RegisterUserRequest("test@example.com", "password", Set.of("CUSTOMER"));

        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRoleRepository.findByName(anyString())).thenReturn(Optional.of(new UserRole("ROLE_USER")));

        // Act
        Map<String, Object> response = authenticationService.registerUser(request);

        // Assert
        assertNotNull(response);
        assertTrue(response.containsKey("message"));
        assertEquals("User test@example.com registered successfully", response.get("message"));
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void testRegisterUser_UserAlreadyExists() {
        // Arrange
        RegisterUserRequest request = new RegisterUserRequest("existing@example.com", "password", Set.of("ROLE_USER"));

        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        // Act
        authenticationService.registerUser(request);

        // Assert: Expects UserAlreadyExistsException
    }

    @Test(expected = RoleNotFoundException.class)
    public void testRegisterUser_RoleNotFound() {
        // Arrange
        RegisterUserRequest request = new RegisterUserRequest("test@example.com", "password", Set.of("ROLE_UNKNOWN"));

        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRoleRepository.findByName(anyString())).thenReturn(Optional.empty());

        // Act
        authenticationService.registerUser(request);

        // Assert: Expects RoleNotFoundException
    }
}
