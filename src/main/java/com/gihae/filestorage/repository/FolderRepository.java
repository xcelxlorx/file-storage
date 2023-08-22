package com.gihae.filestorage.repository;

import com.gihae.filestorage.domain.File;
import com.gihae.filestorage.domain.Folder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FolderRepository {

    void save(Folder folder);

    Optional<Folder> findById(Long id);

    Optional<Folder> findByName(String name);

    List<Folder> findByParentId(Long parentId);

    void deleteById(Long id);
}
