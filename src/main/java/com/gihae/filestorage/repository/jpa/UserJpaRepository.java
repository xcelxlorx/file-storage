package com.gihae.filestorage.repository.jpa;

import com.gihae.filestorage.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(@Param("email") String email);

    @Modifying
    @Query("update User u SET u.usage = :usage where u.id = :userId")
    void updateUsage(@Param("userId") Long userId, @Param("usage") Long usage);
}
