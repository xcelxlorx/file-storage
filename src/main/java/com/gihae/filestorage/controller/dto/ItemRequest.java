package com.gihae.filestorage.controller.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class ItemRequest {

    @Getter
    @Setter
    public static class UploadDTO {
        private String name;
        private MultipartFile file;
    }
}
