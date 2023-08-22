package com.gihae.filestorage.repository;

import com.gihae.filestorage.domain.File;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository {

    void save(File file);

    Optional<File> findById(Long id);

    Optional<File> findByName(String name);

    List<File> findByFolderId(Long folderId);

    void deleteById(Long id);
}
