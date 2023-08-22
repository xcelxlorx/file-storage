package com.gihae.filestorage.controller.dto;

import com.gihae.filestorage.domain.Folder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class FolderResponse {

    @Getter
    public static class FindFolderDTO{

        List<FolderDTO> folders;

        public FindFolderDTO(List<Folder> folders) {
            this.folders = folders.stream().map(FolderDTO::new).collect(Collectors.toList());
        }

        @Getter
        public class FolderDTO{

            private final Long id;
            private final String name;

            public FolderDTO(Folder folder) {
                this.id = folder.getId();
                this.name = folder.getName();
            }
        }
    }
}
