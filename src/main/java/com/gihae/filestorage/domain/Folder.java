package com.gihae.filestorage.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Getter
@RequiredArgsConstructor
@Entity
@Table(name = "folder_tb")
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    private User user;

    @OneToOne
    private Folder folder;

    public Folder(Long id, String name, User user, Folder folder) {
        this.id = id;
        this.name = name;
        this.user = user;
        this.folder = folder;
    }
}
