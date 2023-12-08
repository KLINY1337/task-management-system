package com.chernomurov.effectivemobile.test.task.management.system.repository;

import com.chernomurov.effectivemobile.test.task.management.system.entity.CustomerTask;
import com.chernomurov.effectivemobile.test.task.management.system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerTaskRepository extends JpaRepository<CustomerTask, Long> {
    @Query("select c from CustomerTask c join c.contractors contractor where contractor = ?1 order by c.creationDateTime desc")
    List<CustomerTask> findAllTasksDelegatedToContractorOrderedByCreationDateTimeFromNewToOld(User contractor);
    @Query("select c from CustomerTask c where c.customer = ?1 order by c.creationDateTime desc")
    List<CustomerTask> findAllTasksOfCustomerOrderedByCreationDateTimeFromNewToOld(User customer);

}
