package com.gihae.filestorage.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class FolderRequest {

    @Getter
    @Setter
    public static class SaveDTO {
        private String name;
    }
}
