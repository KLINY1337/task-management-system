package com.chernomurov.effectivemobile.test.task.management.system.repository;

import com.chernomurov.effectivemobile.test.task.management.system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Transactional
    @Modifying
    @Query("delete from User u where u.email = ?1")
    void deleteByEmail(String email);
    @Query("select (count(u) > 0) from User u where u.email = ?1")
    boolean existsByEmail(String email);
    @Query("select u from User u where u.email = ?1")
    Optional<User> findByEmail(String email);

}
