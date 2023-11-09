package com.gihae.filestorage.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class SaveFile {

    private String originalFileName;
    private String saveFileName;

    protected SaveFile(){}

    public SaveFile(String uploadFileName, String saveFileName) {
        this.originalFileName = uploadFileName;
        this.saveFileName = saveFileName;
    }
}
