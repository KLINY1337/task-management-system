package com.chernomurov.effectivemobile.test.task.management.system.service;

import com.chernomurov.effectivemobile.test.task.management.system.ResponsePage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TaskCommentServiceImpl implements TaskCommentService {
    @Override
    public Map<String, Object> addCommentToTaskByTaskId(Long taskId, String comment) {
        return null;
    }

    @Override
    public Map<String, Set<ResponsePage>> getAllCommentsByTaskId(Long id) {
        return null;
    }
}
