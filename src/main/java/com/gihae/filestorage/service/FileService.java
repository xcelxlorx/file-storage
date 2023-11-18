package com.gihae.filestorage.service;

import com.gihae.filestorage._core.errors.exception.*;
import com.gihae.filestorage.controller.dto.FileRequest;
import com.gihae.filestorage.controller.dto.FileResponse;
import com.gihae.filestorage.domain.File;
import com.gihae.filestorage.domain.FileData;
import com.gihae.filestorage.domain.Folder;
import com.gihae.filestorage.domain.User;
import com.gihae.filestorage.repository.FileRepository;
import com.gihae.filestorage.repository.FolderRepository;
import com.gihae.filestorage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class FileService {

    private final FileRepository fileRepository;
    private final FolderRepository folderRepository;
    private final UserRepository userRepository;

    private final S3Service s3Service; //s3에 파일 저장
    private final DirService dirService; //로컬에 파일 저장

    public FileResponse.FindFileDTO findByFolderId(Long folderId){
        List<File> files = fileRepository.findByFolderId(folderId);
        return new FileResponse.FindFileDTO(files);
    }

    @Transactional
    public void upload(FileRequest.UploadDTO uploadDTO, Long folderId, Long userId) {
        User user = getUser(userId);

        fileRepository.findByName(uploadDTO.getFile().getOriginalFilename()).ifPresent(file -> {
            throw new ApiException(ExceptionCode.FILE_NAME_EXISTED);
        });

        Folder folder = folderRepository.findById(folderId).orElseThrow(
                () -> new ApiException(ExceptionCode.PARENT_FOLDER_NOT_FOUND)
        );

        MultipartFile file = uploadDTO.getFile();
        FileData fileData = transfer(file);

        try {
            //dirService.upload(fileData, file);
            s3Service.upload(fileData, file);
        } catch (IOException e) {
            throw new ApiException(ExceptionCode.FILE_UPLOAD_FAILED);
        }

        save(user, uploadDTO.getFile(), folder, fileData);
    }

    private void save(User user, MultipartFile multipartFile, Folder folder, FileData fileData) {
        File file = File.builder()
                .name(fileData.getOriginalFileName())
                .fileData(fileData)
                .size(multipartFile.getSize())
                .user(user)
                .folder(folder)
                .build();

        fileRepository.save(file);

        user.updateUsage(user.getTotalUsage() + file.getSize());
    }

    @Transactional
    public ResponseEntity<?> download(Long fileId) {
        File file = fileRepository.findById(fileId).orElseThrow(
                () -> new ApiException(ExceptionCode.FILE_NOT_FOUND)
        );
        FileData fileData = file.getFileData();

        try {
            //return dirService.downloadFile(fileData.getOriginalFileName(), fileData.getSaveFileName());
            return s3Service.download(fileData.getOriginalFileName(), fileData.getSaveFileName());
        } catch (IOException e) {
            throw new ApiException(ExceptionCode.FILE_DOWNLOAD_FAILED);
        }
    }

    @Transactional
    public void delete(Long itemId, Long userId) {
        User user = getUser(userId);

        File file = fileRepository.findById(itemId).orElseThrow(
                () -> new ApiException(ExceptionCode.FILE_NOT_FOUND)
        );

        FileData fileData = file.getFileData();

        //dirService.delete(fileData.getSaveFileName());
        s3Service.delete(fileData.getSaveFileName());
        fileRepository.deleteById(itemId);

        user.updateUsage(user.getTotalUsage() - file.getSize());
    }

    private FileData transfer(MultipartFile file){
        if(file.isEmpty()){
            throw new ApiException(ExceptionCode.FILE_NOT_FOUND);
        }

        String originalFilename = file.getOriginalFilename();
        if(originalFilename == null){
            throw new ApiException(ExceptionCode.FILE_NAME_EMPTY);
        }

        int pos = originalFilename.lastIndexOf(".");
        String ext = originalFilename.substring(pos + 1);
        String uuid = UUID.randomUUID().toString();
        String saveFileName = uuid + "." + ext;

        return new FileData(originalFilename, saveFileName);
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new ApiException(ExceptionCode.USER_NOT_FOUND)
        );
    }
}
