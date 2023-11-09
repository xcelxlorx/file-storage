package com.gihae.filestorage.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class FileData {

    private String originalFileName;
    private String saveFileName;

    protected FileData(){}

    public FileData(String uploadFileName, String saveFileName) {
        this.originalFileName = uploadFileName;
        this.saveFileName = saveFileName;
    }
}
