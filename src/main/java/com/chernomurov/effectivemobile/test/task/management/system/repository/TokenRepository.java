package com.chernomurov.effectivemobile.test.task.management.system.repository;

import com.chernomurov.effectivemobile.test.task.management.system.entity.Token;
import com.chernomurov.effectivemobile.test.task.management.system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    @Transactional
    @Modifying
    @Query("update Token t set t.isRevoked = true where t.user = ?1 and t.isRevoked = false and t.isExpired = false")
    void RevokeAllValidUserTokens(User user);
    @Query("select t from Token t where t.value = ?1")
    Optional<Token> findByValue(String value);
}
