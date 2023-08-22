package com.gihae.filestorage.repository.jpa;

import com.gihae.filestorage.domain.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FolderJpaRepository extends JpaRepository<Folder, Long> {

    @Modifying
    @Query("delete Folder f where f.id = :folderId")
    void deleteById(@Param("folderId") Long folderId);

    @Query("select f from Folder f where f.parent.id = :parentId")
    List<Folder> findByParentId(@Param("parentId") Long parentId);

    @Query("select f from Folder f where f.name = :name")
    Optional<Folder> findByName(@Param("name") String name);
}
