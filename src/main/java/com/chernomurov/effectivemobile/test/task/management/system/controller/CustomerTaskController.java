package com.chernomurov.effectivemobile.test.task.management.system.controller;

import com.chernomurov.effectivemobile.test.task.management.system.ResponsePage;
import com.chernomurov.effectivemobile.test.task.management.system.entity.CustomerTask;
import com.chernomurov.effectivemobile.test.task.management.system.request.CreateTaskRequest;
import com.chernomurov.effectivemobile.test.task.management.system.request.UpdateCustomerTaskRequest;
import com.chernomurov.effectivemobile.test.task.management.system.service.CustomerTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/task")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class CustomerTaskController{

    private final CustomerTaskService customerTaskService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createCustomerTask(@RequestBody CreateTaskRequest request) {
        return ResponseEntity.ok(customerTaskService.createCustomerTask(request));
    }


    @GetMapping("/{id}")
    public ResponseEntity<CustomerTask> getCustomerTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(customerTaskService.getCustomerTaskById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateCustomerTaskById(@PathVariable Long id, @RequestBody UpdateCustomerTaskRequest request) {
        return ResponseEntity.ok(customerTaskService.updateCustomerTaskById(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteCustomerTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(customerTaskService.deleteCustomerTaskById(id));
    }

    @GetMapping("/customer/{id}/tasks")
    public ResponseEntity<Map<String, Set<ResponsePage>>> getAllCustomerTasksByCustomerId(@PathVariable Long id) {
        return ResponseEntity.ok(customerTaskService.getAllCustomerTasksByCustomerId(id));
    }

    @GetMapping("/contractor/{id}/tasks")
    public ResponseEntity<Map<String, Set<ResponsePage>>> getAllContractorTasksByContractorId(@PathVariable Long id) {
        return ResponseEntity.ok(customerTaskService.getAllContractorTasksByContractorId(id));
    }
}
