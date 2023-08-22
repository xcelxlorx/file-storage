package com.gihae.filestorage.repository.jpa;

import com.gihae.filestorage.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FileJpaRepository extends JpaRepository<File, Long> {

    @Query("select f from File f where f.id = :fileId")
    Optional<File> findById(@Param("fileId") Long fileId);

    @Query("select f from File f where f.name = :name")
    Optional<File> findByName(@Param("name") String name);

    @Modifying
    @Query("delete File f where f.id = :fileId")
    void deleteById(@Param("fileId") Long fileId);

    @Query("select f from File f where f.parent.id = :folderId")
    List<File> findByFolderId(@Param("folderId") Long folderId);
}
