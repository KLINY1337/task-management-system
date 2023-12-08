package com.chernomurov.effectivemobile.test.task.management.system.service.implementation;

import com.chernomurov.effectivemobile.test.task.management.system.entity.CustomerTask;
import com.chernomurov.effectivemobile.test.task.management.system.entity.TaskComment;
import com.chernomurov.effectivemobile.test.task.management.system.entity.User;
import com.chernomurov.effectivemobile.test.task.management.system.custom.exception.TaskNotFoundException;
import com.chernomurov.effectivemobile.test.task.management.system.custom.exception.UnauthorizedCustomerTaskAccessException;
import com.chernomurov.effectivemobile.test.task.management.system.repository.CustomerTaskRepository;
import com.chernomurov.effectivemobile.test.task.management.system.repository.TaskCommentRepository;
import com.chernomurov.effectivemobile.test.task.management.system.repository.UserRoleRepository;
import com.chernomurov.effectivemobile.test.task.management.system.service.TaskCommentService;
import com.chernomurov.effectivemobile.test.task.management.system.util.ResponsePageUtils;
import com.chernomurov.effectivemobile.test.task.management.system.util.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TaskCommentServiceImpl implements TaskCommentService {

    private final CustomerTaskRepository customerTaskRepository;
    private final TaskCommentRepository taskCommentRepository;
    private final UserRoleRepository userRoleRepository;

    @Override
    public Map<String, Object> addCommentToTaskByTaskId(Long taskId, String comment) {
        CustomerTask task = customerTaskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException("ERROR -> Task not found (id: '" + taskId + "') ; Class: " + this.getClass().getName()));
        TaskComment taskComment = TaskComment.builder()
                .task(task)
                .value(comment)
                .creationDateTime(LocalDateTime.now())
                .author(UserUtils.getCurrentUser())
                .build();
        taskCommentRepository.save(taskComment);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Task comment created successfully");
        return response;
    }

    @Override
    public Map<String, Set<ResponsePageUtils.ResponsePage>> getAllCommentsByTaskId(Long id) {
        User currentUser = UserUtils.getCurrentUser();
        CustomerTask task = customerTaskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("ERROR -> Task not found (id: '" + id + "') ; Class: " + this.getClass().getName()));
        if (!currentUser.getRoles().contains(userRoleRepository.findByName("CUSTOMER").get())) {
            if (!currentUser.getAcceptedTasks().contains(task)) {
                throw new UnauthorizedCustomerTaskAccessException("ERROR -> Can't access selected task");
            }
        }

        List<TaskComment> taskComments = taskCommentRepository.findAllTaskCommentsByTaskIdOrderedByCreationDateTimeFromNewToOld(id);
        SortedSet<ResponsePageUtils.ResponsePage> paginatedTaskComments = ResponsePageUtils.getPaginatedObjects(taskComments.stream().map(t -> (Object) t).toList());

        Map<String, Set<ResponsePageUtils.ResponsePage>> response = new HashMap<>();
        response.put("pages", paginatedTaskComments);
        return response;
    }
}
