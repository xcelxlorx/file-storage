package com.gihae.filestorage.controller.dto;

import com.gihae.filestorage.domain.File;
import com.gihae.filestorage.domain.FileData;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class FileResponse {

    @Getter
    public static class FindFileDTO{

        List<FileDTO> files;

        public FindFileDTO(List<File> files) {
            this.files = files.stream().map(FileDTO::new).collect(Collectors.toList());
        }

        @Getter
        public class FileDTO{

            private final Long id;
            private final String name;
            private final FileData fileData;
            private final Long size;

            public FileDTO(File fileData) {
                this.id = fileData.getId();
                this.name = fileData.getName();
                this.fileData = fileData.getFileData();
                this.size = fileData.getSize();
            }
        }
    }
}
