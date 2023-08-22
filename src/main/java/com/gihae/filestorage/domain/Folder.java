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

    private String name;

//    @ManyToOne
//    private User user;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Folder parent;

    @OneToMany(mappedBy = "parent")
    private List<Folder> child = new ArrayList<>();

    @Builder
    public Folder(Long id, String name, Folder parent, List<Folder> child) {
        this.id = id;
        this.name = name;
        this.parent = parent;
        this.child = child;
    }
}
