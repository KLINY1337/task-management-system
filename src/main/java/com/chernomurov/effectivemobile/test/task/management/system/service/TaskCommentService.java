package com.chernomurov.effectivemobile.test.task.management.system.service;

import com.chernomurov.effectivemobile.test.task.management.system.ResponsePage;

import java.util.Map;
import java.util.Set;

public interface TaskCommentService {
    Map<String, Object> addCommentToTaskByTaskId(Long taskId, String comment);
    Map<String, Set<ResponsePage>> getAllCommentsByTaskId(Long id);
}
