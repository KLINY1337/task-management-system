package com.chernomurov.effectivemobile.test.task.management.system.repository;

import com.chernomurov.effectivemobile.test.task.management.system.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    @Transactional
    @Modifying
    @Query("delete from UserRole u where u.name = ?1")
    void deleteByName(String name);
    @Query("select u from UserRole u where u.name = ?1")
    Optional<UserRole> findByName(String name);
}
