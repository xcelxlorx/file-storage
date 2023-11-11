package com.gihae.filestorage.service;

import com.gihae.filestorage._core.errors.exception.Exception400;
import com.gihae.filestorage._core.errors.exception.Exception404;
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

    public FolderResponse.FindFolderDTO findByParentId(Long parentId){
        List<Folder> folders = folderRepository.findByParentId(parentId);
        return new FolderResponse.FindFolderDTO(folders);
    }

    @Transactional
    public void create(FolderRequest.SaveDTO saveDTO, Long parentId){
        folderRepository.findByName(saveDTO.getName()).ifPresent(folder -> {
            throw new Exception400("동일한 이름의 폴더가 존재합니다.");
        });

        Folder parent = folderRepository.findById(parentId).orElseThrow(
                () -> new Exception404("해당 상위 폴더가 존재하지 않습니다.")
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
    public void download(){

    }

    @Transactional
    public void delete(Long folderId){
        folderRepository.deleteById(folderId);
    }
}
