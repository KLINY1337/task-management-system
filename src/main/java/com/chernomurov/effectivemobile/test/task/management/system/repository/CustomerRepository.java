package com.chernomurov.effectivemobile.test.task.management.system.repository;

import com.chernomurov.effectivemobile.test.task.management.system.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
