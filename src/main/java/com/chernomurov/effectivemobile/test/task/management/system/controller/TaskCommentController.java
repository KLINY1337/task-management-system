package com.chernomurov.effectivemobile.test.task.management.system.controller;

import com.chernomurov.effectivemobile.test.task.management.system.service.TaskCommentService;
import com.chernomurov.effectivemobile.test.task.management.system.util.ResponsePageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/comment/")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class TaskCommentController {

    private final TaskCommentService taskCommentService;

    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'CONTRACTOR')")
    @PostMapping("/{id}")
    public ResponseEntity<Map<String, Object>> addCommentToTaskByTaskId(@PathVariable Long id, @RequestBody String comment) {
        return ResponseEntity.ok(taskCommentService.addCommentToTaskByTaskId(id, comment));
    }

    @PreAuthorize("hasAnyAuthority('CUSTOMER', 'CONTRACTOR')")
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Set<ResponsePageUtils.ResponsePage>>> getAllCommentsByTaskId(@PathVariable Long id) {
        return ResponseEntity.ok(taskCommentService.getAllCommentsByTaskId(id));
    }
}
