package com.chernomurov.effectivemobile.test.task.management.system.custom.dto;

import com.chernomurov.effectivemobile.test.task.management.system.entity.enumeration.TaskPriority;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link com.chernomurov.effectivemobile.test.task.management.system.entity.CustomerTask}
 */
@Data
public class CreateTaskRequest implements Serializable {

    @Nullable
    private final String title;

    @NotNull(message = "ERROR -> Description is not specified")
    @NotBlank(message = "ERROR -> Description can't be empty")
    private final String description;

    @NotNull(message = "ERROR -> Priority is not specified")
    @NotEmpty(message = "ERROR -> Priority can't be empty")
    private final TaskPriority priority;

    @Nullable
    private final Set<String> contractorEmails;

}