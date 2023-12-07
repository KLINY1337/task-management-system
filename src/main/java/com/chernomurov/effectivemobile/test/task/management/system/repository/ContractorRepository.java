package com.chernomurov.effectivemobile.test.task.management.system.repository;

import com.chernomurov.effectivemobile.test.task.management.system.entity.Contractor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContractorRepository extends JpaRepository<Contractor, Long> {
    @Query("select c from Contractor c where c.email = ?1")
    Optional<Contractor> findByEmail(String email);
}
