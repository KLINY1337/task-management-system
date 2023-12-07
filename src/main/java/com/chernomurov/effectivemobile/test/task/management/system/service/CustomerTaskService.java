package com.chernomurov.effectivemobile.test.task.management.system.service;

import com.chernomurov.effectivemobile.test.task.management.system.ResponsePage;
import com.chernomurov.effectivemobile.test.task.management.system.TaskStatus;
import com.chernomurov.effectivemobile.test.task.management.system.entity.CustomerTask;
import com.chernomurov.effectivemobile.test.task.management.system.entity.TaskComment;
import com.chernomurov.effectivemobile.test.task.management.system.request.CreateTaskRequest;
import com.chernomurov.effectivemobile.test.task.management.system.request.UpdateCustomerTaskRequest;

import java.util.Map;
import java.util.Set;

public interface CustomerTaskService {
    Map<String, Object> createCustomerTask(CreateTaskRequest request);
    CustomerTask getCustomerTaskById(Long id);
    Map<String, Object> updateCustomerTaskById(Long id, UpdateCustomerTaskRequest request);
    Map<String, Object> deleteCustomerTaskById(Long id);

    Map<String, Set<ResponsePage>> getAllCustomerTasksByCustomerId(Long id);
    Map<String, Set<ResponsePage>> getAllContractorTasksByContractorId(Long id);
    Map<String, Object> updateContractorTaskStatusByTaskId(Long id, TaskStatus status);
}
