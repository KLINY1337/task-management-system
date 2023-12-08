package com.chernomurov.effectivemobile.test.task.management.system.service.implementation;

import com.chernomurov.effectivemobile.test.task.management.system.custom.dto.CreateTaskRequest;
import com.chernomurov.effectivemobile.test.task.management.system.custom.dto.UpdateCustomerTaskRequest;
import com.chernomurov.effectivemobile.test.task.management.system.custom.exception.CustomerNotFoundException;
import com.chernomurov.effectivemobile.test.task.management.system.custom.exception.TaskNotFoundException;
import com.chernomurov.effectivemobile.test.task.management.system.entity.CustomerTask;
import com.chernomurov.effectivemobile.test.task.management.system.entity.User;
import com.chernomurov.effectivemobile.test.task.management.system.entity.UserRole;
import com.chernomurov.effectivemobile.test.task.management.system.entity.enumeration.TaskPriority;
import com.chernomurov.effectivemobile.test.task.management.system.entity.enumeration.TaskStatus;
import com.chernomurov.effectivemobile.test.task.management.system.repository.CustomerTaskRepository;
import com.chernomurov.effectivemobile.test.task.management.system.repository.UserRepository;
import com.chernomurov.effectivemobile.test.task.management.system.repository.UserRoleRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerTaskServiceTest {

    @Mock
    private CustomerTaskRepository customerTaskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserRoleRepository userRoleRepository;

    @InjectMocks
    private CustomerTaskServiceImpl customerTaskService;

    @Test
    public void testCreateCustomerTask_Success() {
        // Arrange
        CreateTaskRequest request = new CreateTaskRequest("Test Task", "Test Description", TaskPriority.HIGH, Set.of("contractor@example.com"));

        User currentUser = new User();
        currentUser.setId(1L);
        currentUser.setEmail("customer@example.com");

        UserRole roleContractor = new UserRole("CONTRACTOR");

        User contractor = new User();
        contractor.setId(2L);
        contractor.setEmail("contractor@example.com");
        contractor.setRoles(Set.of());

        when(userRepository.findById(currentUser.getId())).thenReturn(Optional.of(currentUser));
        when(userRepository.findByEmail("contractor@example.com")).thenReturn(Optional.of(contractor));
        when(customerTaskRepository.save(any(CustomerTask.class))).thenReturn(new CustomerTask());
        when(userRoleRepository.findByName("CONTRACTOR")).thenReturn(Optional.of(roleContractor));

        // Act
        Map<String, Object> response = customerTaskService.createCustomerTask(request);

        // Assert
        assertNotNull(response);
        assertTrue(response.containsKey("message"));
        assertEquals("Task created successfully", response.get("message"));
    }
}
