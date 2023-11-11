package com.gihae.filestorage.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Getter
@RequiredArgsConstructor
@Entity
@Table(name = "files_tb")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long size;

    @Embedded
    private FileData fileData;

//    @ManyToOne
//    private User user;

    @ManyToOne
    private Folder folder;

    @Builder
    public File(Long id, String name, Long size, FileData fileData, Folder folder) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.fileData = fileData;
        this.folder = folder;
    }
}
