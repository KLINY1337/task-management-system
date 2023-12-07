package com.chernomurov.effectivemobile.test.task.management.system.controller;

import com.chernomurov.effectivemobile.test.task.management.system.ResponsePage;
import com.chernomurov.effectivemobile.test.task.management.system.service.TaskCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/comment/")
@RequiredArgsConstructor
public class TaskCommentController {

    private final TaskCommentService taskCommentService;

    @PostMapping("/{id}")
    public ResponseEntity<Map<String, Object>> addCommentToTaskByTaskId(@PathVariable Long id, @RequestBody String comment) {
        return ResponseEntity.ok(taskCommentService.addCommentToTaskByTaskId(id, comment));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Set<ResponsePage>>> getAllCommentsByTaskId(@PathVariable Long id) {
        return ResponseEntity.ok(taskCommentService.getAllCommentsByTaskId(id));
    }
}
