package com.gihae.filestorage.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
@Entity
@Table(name = "folder_tb")
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 45, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Folder parent;

    @OneToMany(mappedBy = "parent")
    private List<Folder> folders = new ArrayList<>();

    @OneToMany(mappedBy = "folder")
    private List<File> files = new ArrayList<>();

    @Builder
    public Folder(Long id, String name, User user, Folder parent, List<Folder> folders, List<File> files) {
        this.id = id;
        this.name = name;
        this.user = user;
        this.parent = parent;
        this.folders = folders;
        this.files = files;
    }
}
