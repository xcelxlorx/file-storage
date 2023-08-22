package com.gihae.filestorage.domain;

import com.gihae.filestorage.controller.dto.SaveFile;
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
    private SaveFile file;

//    @ManyToOne
//    private User user;

    @ManyToOne
    private Folder parent;

    @Builder
    public File(Long id, String name, Long size, SaveFile file, Folder parent) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.file = file;
        this.parent = parent;
    }
}
