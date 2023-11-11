package com.gihae.filestorage.repository.jpa;

import com.gihae.filestorage.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FileJpaRepository extends JpaRepository<File, Long> {

    Optional<File> findByName(String name);

    List<File> findByFolderId(Long folderId);
}
