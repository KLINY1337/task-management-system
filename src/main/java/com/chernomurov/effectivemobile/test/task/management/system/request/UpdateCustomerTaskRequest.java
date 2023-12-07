package com.chernomurov.effectivemobile.test.task.management.system.request;

import com.chernomurov.effectivemobile.test.task.management.system.TaskPriority;
import com.chernomurov.effectivemobile.test.task.management.system.TaskStatus;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link com.chernomurov.effectivemobile.test.task.management.system.entity.CustomerTask}
 */
@Data
public class UpdateCustomerTaskRequest implements Serializable {

    @Nullable
    private final String title;

    @Nullable
    private final String description;

    @Nullable
    private final TaskStatus status;

    @Nullable
    private final TaskPriority priority;

    @Nullable
    private final Set<String> contractorEmails;

}