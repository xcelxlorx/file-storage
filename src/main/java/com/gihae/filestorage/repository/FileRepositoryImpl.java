package com.gihae.filestorage.repository;

import com.gihae.filestorage.domain.File;
import com.gihae.filestorage.repository.FileRepository;
import com.gihae.filestorage.repository.jpa.FileJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class FileRepositoryImpl implements FileRepository {

    private final FileJpaRepository fileJpaRepository;

    @Override
    public void save(File file) {
        fileJpaRepository.save(file);
    }

    @Override
    public Optional<File> findById(Long id) {
        return fileJpaRepository.findById(id);
    }

    @Override
    public Optional<File> findByName(String name) {
        return fileJpaRepository.findByName(name);
    }

    @Override
    public List<File> findByFolderId(Long folderId) {
        return fileJpaRepository.findByFolderId(folderId);
    }

    @Override
    public void deleteById(Long id) {
        fileJpaRepository.deleteById(id);
    }
}
