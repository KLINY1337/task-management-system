package com.chernomurov.effectivemobile.test.task.management.system.service;

import com.chernomurov.effectivemobile.test.task.management.system.ResponsePage;
import com.chernomurov.effectivemobile.test.task.management.system.TaskPriority;
import com.chernomurov.effectivemobile.test.task.management.system.TaskStatus;
import com.chernomurov.effectivemobile.test.task.management.system.entity.*;
import com.chernomurov.effectivemobile.test.task.management.system.exception.ContractorNotFoundException;
import com.chernomurov.effectivemobile.test.task.management.system.exception.CustomerNotFoundException;
import com.chernomurov.effectivemobile.test.task.management.system.exception.TaskNotFoundException;
import com.chernomurov.effectivemobile.test.task.management.system.exception.UnauthorizedCustomerTaskAccessException;
import com.chernomurov.effectivemobile.test.task.management.system.repository.ContractorRepository;
import com.chernomurov.effectivemobile.test.task.management.system.repository.CustomerRepository;
import com.chernomurov.effectivemobile.test.task.management.system.repository.TaskRepository;
import com.chernomurov.effectivemobile.test.task.management.system.repository.UserRepository;
import com.chernomurov.effectivemobile.test.task.management.system.request.CreateTaskRequest;
import com.chernomurov.effectivemobile.test.task.management.system.request.UpdateCustomerTaskRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerTaskServiceImpl implements CustomerTaskService {

    private final ContractorRepository contractorRepository;
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;

    @Override
    public Map<String, Object> createCustomerTask(CreateTaskRequest request) {

        Set<Contractor> contractors = new HashSet<>();
        Set<String> emails = request.getContractorEmails();
        if (emails != null && !emails.isEmpty()) {
            emails.forEach(email -> {
                Optional<Contractor> contractor = contractorRepository.findByEmail(email);
                if (contractor.isPresent()) {
                    contractors.add(contractor.get());
                }
                else {
                    throw new ContractorNotFoundException("ERROR -> Contractor not found (email: '" + email + "') ; Class: " + this.getClass().getName());
                }
            });
        }

        CustomerTask task = CustomerTask.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(TaskStatus.NOT_ACCEPTED)
                .priority(request.getPriority())
                .contractors(contractors)
                .comments(new HashSet<>())
                .creationDateTime(LocalDateTime.now())
                .build();

        taskRepository.save(task);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Task created successfully");
        return response;
    }

    @Override
    public CustomerTask getCustomerTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("ERROR -> Task not found (id: '" + id + "') ; Class: " + this.getClass().getName()));
    }

    @Override
    public Map<String, Object> updateCustomerTaskById(Long id, UpdateCustomerTaskRequest request) {
        User currentUser = getCurrentUser();
        Customer currentCustomer = customerRepository.findById(currentUser.getId()).orElseThrow(() -> new CustomerNotFoundException("ERROR -> Customer not found (email: '" + currentUser.getEmail() + "') ; Class: " + this.getClass().getName()));
        CustomerTask task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("ERROR -> Task not found (id: '" + id + "') ; Class: " + this.getClass().getName()));

        if (!task.getCustomer().equals(currentCustomer)) {
            throw new UnauthorizedCustomerTaskAccessException("ERROR -> Can't access selected task");
        }

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
            Set<Contractor> contractors = new HashSet<>();
            emails.forEach(email -> {
                //TODO EXTRACT METHOD
                Optional<Contractor> contractor = contractorRepository.findByEmail(email);
                if (contractor.isPresent()) {
                    contractors.add(contractor.get());
                }
                else {
                    throw new ContractorNotFoundException("ERROR -> Contractor not found (email: '" + email + "') ; Class: " + this.getClass().getName());
                }
            });
            task.setContractors(contractors);
        }

        taskRepository.save(task);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Task updated successfully");
        return response;
    }

    @Override
    public Map<String, Object> deleteCustomerTaskById(Long id) {
        User currentUser = getCurrentUser();
        Customer currentCustomer = customerRepository.findById(currentUser.getId()).orElseThrow(() -> new CustomerNotFoundException("ERROR -> Customer not found (email: '" + currentUser.getEmail() + "') ; Class: " + this.getClass().getName()));
        CustomerTask task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("ERROR -> Task not found (id: '" + id + "') ; Class: " + this.getClass().getName()));

        if (!task.getCustomer().equals(currentCustomer)) {
            throw new UnauthorizedCustomerTaskAccessException("ERROR -> Can't access selected task");
        }

        taskRepository.delete(task);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Task deleted successfully");
        return response;
    }

    @Override
    public Map<String, Set<ResponsePage>> getAllCustomerTasksByCustomerId(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("ERROR -> Customer not found (id: '" + id + "') ; Class: " + this.getClass().getName()));
        List<CustomerTask> tasks = taskRepository.findAllTasksOfCustomerOrderedByCreationDateTimeFromNewToOld(customer).orElseThrow(() -> new TaskNotFoundException("ERROR -> Customer with id: '" + id + "' doesn't have any created tasks"));

        SortedSet<ResponsePage> paginatedTasks = new TreeSet<>(Comparator.comparing(ResponsePage::pageNumber));
        int pageSize = 10;
        ResponsePage page = null;
        List<Object> pageObjects = new ArrayList<>(pageSize);
        for (int i = 0; i < tasks.size(); i++) {
            pageObjects.add(tasks.get(i));
            if (i % pageSize == 0) {
                int pageNumber = i / pageSize + 1;
                page = new ResponsePage(pageNumber, pageObjects);
                paginatedTasks.add(page);
                pageObjects = new ArrayList<>(pageSize);
            }
        }
        if (!Objects.requireNonNull(page).objects().isEmpty()) {
            paginatedTasks.add(page);
        }

        Map<String, Set<ResponsePage>> response = new HashMap<>();
        response.put("pages", paginatedTasks);
        return response;
    }

    @Override
    public Map<String, Set<ResponsePage>> getAllContractorTasksByContractorId(Long id) {
        Contractor contractor = contractorRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("ERROR -> Contractor not found (id: '" + id + "') ; Class: " + this.getClass().getName()));
        List<CustomerTask> tasks = taskRepository.findAllTasksDelegatedToContractorOrderedByCreationDateTimeFromNewToOld(contractor).orElseThrow(() -> new TaskNotFoundException("ERROR -> Contractor with id: '" + id + "' doesn't have any accepted tasks"));

        SortedSet<ResponsePage> paginatedTasks = new TreeSet<>(Comparator.comparing(ResponsePage::pageNumber));
        int pageSize = 10;
        ResponsePage page = null;
        List<Object> pageObjects = new ArrayList<>(pageSize);
        for (int i = 0; i < tasks.size(); i++) {
            pageObjects.add(tasks.get(i));
            if (i % pageSize == 0) {
                int pageNumber = i / pageSize + 1;
                page = new ResponsePage(pageNumber, pageObjects);
                paginatedTasks.add(page);
                pageObjects = new ArrayList<>(pageSize);
            }
        }
        if (!Objects.requireNonNull(page).objects().isEmpty()) {
            paginatedTasks.add(page);
        }

        Map<String, Set<ResponsePage>> response = new HashMap<>();
        response.put("pages", paginatedTasks);
        return response;
    }

    @Override
    public Map<String, Object> updateContractorTaskStatusByTaskId(Long id, TaskStatus status) {
        return null;
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserEmail = authentication.getName();
            return userRepository.findByEmail(currentUserEmail).orElseThrow(() -> new UsernameNotFoundException("ERROR -> Authenticated user not found in database (Email: '" + currentUserEmail + "') ; Class: " + this.getClass().getName()));
        }
        throw new UsernameNotFoundException("CRITICAL ERROR -> UNREACHABLE APPLICATION STATEMENT ; Class: " + this.getClass().getName());
    }
}
