package com.chernomurov.effectivemobile.test.task.management.system.repository;

import com.chernomurov.effectivemobile.test.task.management.system.entity.CustomerTask;
import com.chernomurov.effectivemobile.test.task.management.system.entity.TaskComment;
import com.chernomurov.effectivemobile.test.task.management.system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskCommentRepository extends JpaRepository<TaskComment, Long> {
    @Query("select t from TaskComment t where t.task.id = ?1 order by t.creationDateTime desc")
    List<TaskComment> findAllTaskCommentsByTaskIdOrderedByCreationDateTimeFromNewToOld(Long taskId);
}
