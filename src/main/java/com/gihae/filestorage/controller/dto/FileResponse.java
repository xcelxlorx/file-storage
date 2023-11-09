package com.gihae.filestorage.controller.dto;

import com.gihae.filestorage.domain.File;
import com.gihae.filestorage.domain.SaveFile;
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
            private final SaveFile file;
            private final Long size;

            public FileDTO(File file) {
                this.id = file.getId();
                this.name = file.getName();
                this.file = file.getFile();
                this.size = file.getSize();
            }
        }
    }
}
