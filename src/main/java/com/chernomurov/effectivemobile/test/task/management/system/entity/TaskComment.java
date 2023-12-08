package com.chernomurov.effectivemobile.test.task.management.system.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private CustomerTask task;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    private String value;

    private LocalDateTime creationDateTime;
}
