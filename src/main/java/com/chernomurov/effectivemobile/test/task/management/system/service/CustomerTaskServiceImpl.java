package com.chernomurov.effectivemobile.test.task.management.system.service;

import com.chernomurov.effectivemobile.test.task.management.system.ResponsePage;
import com.chernomurov.effectivemobile.test.task.management.system.entity.CustomerTask;
import com.chernomurov.effectivemobile.test.task.management.system.entity.UserRole;
import com.chernomurov.effectivemobile.test.task.management.system.request.CreateTaskRequest;
import com.chernomurov.effectivemobile.test.task.management.system.request.UpdateCustomerTaskRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomerTaskServiceImpl implements CustomerTaskService {
    @Override
    public Map<String, Object> createCustomerTask(CreateTaskRequest request) {
        return null;
    }

    @Override
    public CustomerTask getCustomerTaskById(Long id) {
        return null;
    }

    @Override
    public Map<String, Object> updateCustomerTaskById(Long id, UpdateCustomerTaskRequest request) {
        return null;
    }

    @Override
    public Map<String, Object> deleteCustomerTaskById(Long id) {
        return null;
    }

    @Override
    public Map<String, Set<ResponsePage>> getAllCustomerTasksByCustomerId(Long id) {
        return null;
    }

    @Override
    public Map<String, Set<ResponsePage>> getAllContractorTasksByContractorId(Long id) {
        return null;
    }

}
