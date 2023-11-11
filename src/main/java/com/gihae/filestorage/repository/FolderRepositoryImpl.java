package com.gihae.filestorage.repository;

import com.gihae.filestorage.domain.Folder;
import com.gihae.filestorage.repository.jpa.FolderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class FolderRepositoryImpl implements FolderRepository {

    private final FolderJpaRepository folderJpaRepository;

    @Override
    public void save(Folder folder) {
        folderJpaRepository.save(folder);
    }

    @Override
    public Optional<Folder> findById(Long id) {
        return folderJpaRepository.findById(id);
    }

    @Override
    public Optional<Folder> findByName(String name) {
        return folderJpaRepository.findByName(name);
    }

    @Override
    public List<Folder> findByParentId(Long parentId) {
        return folderJpaRepository.findByParentId(parentId);
    }

    @Override
    public void deleteById(Long id) {
        folderJpaRepository.deleteById(id);
    }
}
