package com.chernomurov.effectivemobile.test.task.management.system.service;

import com.chernomurov.effectivemobile.test.task.management.system.entity.enumeration.TaskStatus;
import com.chernomurov.effectivemobile.test.task.management.system.entity.CustomerTask;
import com.chernomurov.effectivemobile.test.task.management.system.custom.dto.CreateTaskRequest;
import com.chernomurov.effectivemobile.test.task.management.system.custom.dto.UpdateCustomerTaskRequest;
import com.chernomurov.effectivemobile.test.task.management.system.util.ResponsePageUtils;

import java.util.Map;
import java.util.Set;

public interface CustomerTaskService {
    Map<String, Object> createCustomerTask(CreateTaskRequest request);
    CustomerTask getCustomerTaskById(Long id);
    Map<String, Object> updateCustomerTaskById(Long id, UpdateCustomerTaskRequest request);
    Map<String, Object> deleteCustomerTaskById(Long id);

    Map<String, Set<ResponsePageUtils.ResponsePage>> getAllCustomerTasksByCustomerId(Long id);
    Map<String, Set<ResponsePageUtils.ResponsePage>> getAllContractorTasksByContractorId(Long id);
    Map<String, Object> updateContractorTaskStatusByTaskId(Long id, TaskStatus status);
}
