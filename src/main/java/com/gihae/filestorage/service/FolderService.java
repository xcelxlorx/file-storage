package com.gihae.filestorage.service;

import com.gihae.filestorage._core.errors.exception.ApiException;
import com.gihae.filestorage._core.errors.exception.ExceptionCode;
import com.gihae.filestorage.controller.dto.FolderRequest;
import com.gihae.filestorage.controller.dto.FolderResponse;
import com.gihae.filestorage.domain.Folder;
import com.gihae.filestorage.repository.FolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class FolderService {

    private final FolderRepository folderRepository;

    private final FileService fileService;

    public FolderResponse.FindFolderDTO findByParentId(Long parentId){
        List<Folder> folders = folderRepository.findByParentId(parentId);
        return new FolderResponse.FindFolderDTO(folders);
    }

    @Transactional
    public void create(FolderRequest.SaveDTO saveDTO, Long parentId){
        folderRepository.findByName(saveDTO.getName()).ifPresent(folder -> {
            throw new ApiException(ExceptionCode.FOLDER_NAME_EXISTED);
        });

        Folder parent = folderRepository.findById(parentId).orElseThrow(
                () -> new ApiException(ExceptionCode.PARENT_FOLDER_NOT_FOUND)
        );

        save(saveDTO, parent);
    }

    private void save(FolderRequest.SaveDTO saveDTO, Folder parent) {
        Folder folder = Folder.builder()
                .name(saveDTO.getName())
                .parent(parent)
                .build();

        folderRepository.save(folder);
    }

    @Transactional
    public void download(Long folderId){

    }

    @Transactional
    public void delete(Long folderId){
        folderRepository.deleteById(folderId);
    }
}
