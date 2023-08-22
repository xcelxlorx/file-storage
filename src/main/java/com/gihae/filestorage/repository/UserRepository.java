package com.gihae.filestorage.repository;

import com.gihae.filestorage.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository {

    void save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    void updateUsage(Long id, Long usage);
}
