package com.gihae.filestorage.controller.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class FileRequest {

    @Getter
    @Setter
    public static class UploadDTO {
        private MultipartFile file;
    }
}
