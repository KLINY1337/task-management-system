package com.chernomurov.effectivemobile.test.task.management.system.repository;

import com.chernomurov.effectivemobile.test.task.management.system.entity.Contractor;
import com.chernomurov.effectivemobile.test.task.management.system.entity.Customer;
import com.chernomurov.effectivemobile.test.task.management.system.entity.CustomerTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface TaskRepository extends JpaRepository<CustomerTask, Long> {
    @Query("select c from CustomerTask c where ?1 in c.contractors")
    Optional<List<CustomerTask>> findAllTasksDelegatedToContractorOrderedByCreationDateTimeFromNewToOld(Contractor contractor);
    @Query("select c from CustomerTask c where c.customer = ?1 order by c.creationDateTime desc")
    Optional<List<CustomerTask>> findAllTasksOfCustomerOrderedByCreationDateTimeFromNewToOld(Customer customer);

}
