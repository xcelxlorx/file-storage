package com.gihae.filestorage.repository.impl;

import com.gihae.filestorage.domain.User;
import com.gihae.filestorage.repository.UserRepository;
import com.gihae.filestorage.repository.jpa.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public void save(User user) {
        userJpaRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userJpaRepository.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email);
    }

    @Override
    public void updateUsage(Long id, Long usage) {
        userJpaRepository.updateUsage(id, usage);
    }
}
