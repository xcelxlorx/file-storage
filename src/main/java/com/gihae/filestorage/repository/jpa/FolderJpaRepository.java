package com.gihae.filestorage.repository.jpa;

import com.gihae.filestorage.domain.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FolderJpaRepository extends JpaRepository<Folder, Long> {

    List<Folder> findByParentId(Long parentId);

    Optional<Folder> findByName(String name);
}
