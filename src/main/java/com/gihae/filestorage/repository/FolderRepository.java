package com.gihae.filestorage.repository;

import com.gihae.filestorage.domain.Folder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FolderRepository extends JpaRepository<Folder, Long> {
}
