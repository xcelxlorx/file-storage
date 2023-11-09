package com.gihae.filestorage.controller.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class SaveFile {

    private String originalFileName;
    private String saveFileName;
    private MultipartFile file;

    protected SaveFile(){}

    public SaveFile(String uploadFileName, String saveFileName, MultipartFile file) {
        this.originalFileName = uploadFileName;
        this.saveFileName = saveFileName;
        this.file = file;
    }
}
