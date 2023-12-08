package com.chernomurov.effectivemobile.test.task.management.system.controller;

import com.chernomurov.effectivemobile.test.task.management.system.entity.enumeration.TaskStatus;
import com.chernomurov.effectivemobile.test.task.management.system.entity.CustomerTask;
import com.chernomurov.effectivemobile.test.task.management.system.custom.dto.CreateTaskRequest;
import com.chernomurov.effectivemobile.test.task.management.system.custom.dto.UpdateCustomerTaskRequest;
import com.chernomurov.effectivemobile.test.task.management.system.service.CustomerTaskService;
import com.chernomurov.effectivemobile.test.task.management.system.util.ResponsePageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
public class CustomerTaskController{

    private final CustomerTaskService customerTaskService;

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @PostMapping
    public ResponseEntity<Map<String, Object>> createCustomerTask(@RequestBody CreateTaskRequest request) {
        return ResponseEntity.ok(customerTaskService.createCustomerTask(request));
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @GetMapping("/{id}")
    public ResponseEntity<CustomerTask> getCustomerTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(customerTaskService.getCustomerTaskById(id));
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateCustomerTaskById(@PathVariable Long id, @RequestBody UpdateCustomerTaskRequest request) {
        return ResponseEntity.ok(customerTaskService.updateCustomerTaskById(id, request));
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteCustomerTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(customerTaskService.deleteCustomerTaskById(id));
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @GetMapping("/customer/{id}/tasks")
    public ResponseEntity<Map<String, Set<ResponsePageUtils.ResponsePage>>> getAllCustomerTasksByCustomerId(@PathVariable Long id) {
        return ResponseEntity.ok(customerTaskService.getAllCustomerTasksByCustomerId(id));
    }

    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'CONTRACTOR')")
    @GetMapping("/contractor/{id}/tasks")
    public ResponseEntity<Map<String, Set<ResponsePageUtils.ResponsePage>>> getAllContractorTasksByContractorId(@PathVariable Long id) {
        return ResponseEntity.ok(customerTaskService.getAllContractorTasksByContractorId(id));
    }

    @PreAuthorize("hasAuthority('CONTRACTOR')")
    @GetMapping("/{id}/status")
    public ResponseEntity<Map<String, Object>> updateContractorTaskStatusByTaskId(@PathVariable Long id, @RequestBody TaskStatus status) {
        return ResponseEntity.ok(customerTaskService.updateContractorTaskStatusByTaskId(id, status));
    }
}
