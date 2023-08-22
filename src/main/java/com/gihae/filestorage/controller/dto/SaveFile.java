package com.gihae.filestorage.controller.dto;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class SaveFile {

    private String uploadFileName;
    private String saveFileName;

    protected SaveFile(){}

    public SaveFile(String uploadFileName, String saveFileName) {
        this.uploadFileName = uploadFileName;
        this.saveFileName = saveFileName;
    }
}
