package com.chernomurov.effectivemobile.test.task.management.system.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.*;
import org.springframework.scheduling.config.Task;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "user_id", referencedColumnName = "id")
@Getter
@Setter
public class Customer extends User {

    @OneToMany(mappedBy = "customer")
    private Set<CustomerTask> createdTasks;
}
