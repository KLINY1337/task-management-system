package com.chernomurov.effectivemobile.test.task.management.system.service.implementation;

import com.chernomurov.effectivemobile.test.task.management.system.entity.enumeration.TaskPriority;
import com.chernomurov.effectivemobile.test.task.management.system.entity.enumeration.TaskStatus;
import com.chernomurov.effectivemobile.test.task.management.system.custom.exception.ContractorNotFoundException;
import com.chernomurov.effectivemobile.test.task.management.system.custom.exception.TaskNotFoundException;
import com.chernomurov.effectivemobile.test.task.management.system.custom.exception.UnauthorizedCustomerTaskAccessException;
import com.chernomurov.effectivemobile.test.task.management.system.entity.CustomerTask;
import com.chernomurov.effectivemobile.test.task.management.system.entity.User;
import com.chernomurov.effectivemobile.test.task.management.system.exception.*;
import com.chernomurov.effectivemobile.test.task.management.system.repository.CustomerTaskRepository;
import com.chernomurov.effectivemobile.test.task.management.system.repository.UserRepository;
import com.chernomurov.effectivemobile.test.task.management.system.repository.UserRoleRepository;
import com.chernomurov.effectivemobile.test.task.management.system.custom.dto.CreateTaskRequest;
import com.chernomurov.effectivemobile.test.task.management.system.custom.dto.UpdateCustomerTaskRequest;
import com.chernomurov.effectivemobile.test.task.management.system.service.CustomerTaskService;
import com.chernomurov.effectivemobile.test.task.management.system.util.ResponsePageUtils;
import com.chernomurov.effectivemobile.test.task.management.system.util.UserUtils;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CustomerTaskServiceImpl implements CustomerTaskService {

    private final CustomerTaskRepository customerTaskRepository;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private static final int PAGE_SIZE = 5;

    @Override
    public Map<String, Object> createCustomerTask(CreateTaskRequest request) {
        Set<User> contractors = new HashSet<>();
        Set<String> emails = request.getContractorEmails();
        if (emails != null && !emails.isEmpty()) {
            contractors = getContractorsFromEmails(emails);
        }

        CustomerTask task = CustomerTask.builder()
                .customer(userRepository.findById(UserUtils.getCurrentUser().getId()).get())
                .title(request.getTitle())
                .description(request.getDescription())
                .status(TaskStatus.NOT_ACCEPTED)
                .priority(request.getPriority())
                .contractors(contractors)
                .comments(new HashSet<>())
                .creationDateTime(LocalDateTime.now())
                .build();
        customerTaskRepository.save(task);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Task created successfully");
        return response;
    }

    @Override
    public CustomerTask getCustomerTaskById(Long id) {
        return customerTaskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("ERROR -> Task not found (id: '" + id + "') ; Class: " + this.getClass().getName()));
    }

    @Override
    public Map<String, Object> updateCustomerTaskById(Long id, UpdateCustomerTaskRequest request) {
        CustomerTask task = getCustomerTaskByTaskId(id);
        Object requestBodyParameter;

        requestBodyParameter = request.getTitle();
        if (requestBodyParameter != null) {
            task.setTitle((String) requestBodyParameter);
        }

        requestBodyParameter = request.getDescription();
        if (requestBodyParameter != null) {
            task.setDescription((String) requestBodyParameter);
        }

        requestBodyParameter = request.getStatus();
        if (requestBodyParameter != null) {
            task.setStatus((TaskStatus) requestBodyParameter);
        }

        requestBodyParameter = request.getPriority();
        if (requestBodyParameter != null) {
            task.setPriority((TaskPriority) requestBodyParameter);
        }

        requestBodyParameter = request.getContractorEmails();
        if (requestBodyParameter != null) {
            Set<String> emails = (Set<String>) requestBodyParameter;
            task.setContractors(getContractorsFromEmails(emails));
            task.setStatus(TaskStatus.ACCEPTED);
        }
        customerTaskRepository.save(task);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Task updated successfully");
        return response;
    }

    @Override
    public Map<String, Object> deleteCustomerTaskById(Long id) {
        CustomerTask task = getCustomerTaskByTaskId(id);
        customerTaskRepository.delete(task);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Task deleted successfully");
        return response;
    }

    @Override
    public Map<String, Set<ResponsePageUtils.ResponsePage>> getAllCustomerTasksByCustomerId(Long id) {
        User customer = UserUtils.getUserByIdAndCheckRole(id, "CUSTOMER");

        List<CustomerTask> tasks = customerTaskRepository.findAllTasksOfCustomerOrderedByCreationDateTimeFromNewToOld(customer);
        SortedSet<ResponsePageUtils.ResponsePage> paginatedTasks = ResponsePageUtils.getPaginatedObjects(Collections.singletonList(tasks));

        Map<String, Set<ResponsePageUtils.ResponsePage>> response = new HashMap<>();
        response.put("pages", paginatedTasks);
        return response;
    }

    @Override
    public Map<String, Set<ResponsePageUtils.ResponsePage>> getAllContractorTasksByContractorId(Long id) {
        User contractor = UserUtils.getUserByIdAndCheckRole(id, "CONTRACTOR");

        List<CustomerTask> tasks = customerTaskRepository.findAllTasksDelegatedToContractorOrderedByCreationDateTimeFromNewToOld(contractor);
        SortedSet<ResponsePageUtils.ResponsePage> paginatedTasks = ResponsePageUtils.getPaginatedObjects(Collections.singletonList(tasks));

        Map<String, Set<ResponsePageUtils.ResponsePage>> response = new HashMap<>();
        response.put("pages", paginatedTasks);
        return response;
    }

    @Override
    public Map<String, Object> updateContractorTaskStatusByTaskId(Long id, TaskStatus status) {
        CustomerTask task = getCustomerTaskByTaskId(id);
        task.setStatus(status);
        customerTaskRepository.save(task);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Task status updated successfully");
        return response;
    }

    @NotNull
    private Set<User> getContractorsFromEmails(Set<String> emails) {
        Set<User> contractors = new HashSet<>();
        emails.forEach(email -> {
            Optional<User> contractor = userRepository.findByEmail(email);
            if (contractor.isPresent() && contractor.get().getRoles().contains(userRoleRepository.findByName("CONTRACTOR").get())) {
                contractors.add(contractor.get());
            }
            else {
                throw new ContractorNotFoundException("ERROR -> Contractor not found (email: '" + email + "') ; Class: " + this.getClass().getName());
            }
        });
        return contractors;
    }

    @NotNull
    private CustomerTask getCustomerTaskByTaskId(Long id) {
        User currentUser = UserUtils.getCurrentUser();
        CustomerTask task = customerTaskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("ERROR -> Task not found (id: '" + id + "') ; Class: " + this.getClass().getName()));

        if (!task.getCustomer().equals(currentUser)) {
            throw new UnauthorizedCustomerTaskAccessException("ERROR -> Can't access selected task");
        }
        return task;
    }
}
