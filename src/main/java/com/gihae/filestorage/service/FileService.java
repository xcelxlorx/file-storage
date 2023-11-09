package com.gihae.filestorage.service;

import com.gihae.filestorage._core.errors.exception.Exception400;
import com.gihae.filestorage._core.errors.exception.Exception404;
import com.gihae.filestorage.controller.dto.FileRequest;
import com.gihae.filestorage.controller.dto.FileResponse;
import com.gihae.filestorage.domain.File;
import com.gihae.filestorage.domain.Folder;
import com.gihae.filestorage.domain.FileData;
import com.gihae.filestorage.domain.User;
import com.gihae.filestorage.repository.FileRepository;
import com.gihae.filestorage.repository.FolderRepository;
import com.gihae.filestorage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class FileService {

    private final FileRepository fileRepository;
    private final FolderRepository folderRepository;
    private final UserRepository userRepository;

    private final S3Service s3Service;
    private final DirService dirService;

    public FileResponse.FindFileDTO findByFolderId(Long folderId){
        List<File> files = fileRepository.findByFolderId(folderId);
        return new FileResponse.FindFileDTO(files);
    }

    @Transactional
    public void upload(FileRequest.UploadDTO uploadDTO, Long folderId, Long userId) throws IOException {
        fileRepository.findByName(uploadDTO.getFile().getOriginalFilename()).ifPresent(file -> {
            throw new Exception400("동일한 이름의 파일이 존재합니다.");
        });

        Folder parent = folderRepository.findById(folderId).orElseThrow(
                () -> new Exception404("상위 폴더가 존재하지 않습니다.")
        );

        MultipartFile file = uploadDTO.getFile();
        FileData fileData = transfer(file);

        dirService.upload(fileData, file);
        s3Service.upload(fileData, file);

        save(userId, uploadDTO.getFile(), parent, fileData);
    }

    private void save(Long userId, MultipartFile multipartFile, Folder parent, FileData fileData) {
        File file = File.builder()
                .name(fileData.getOriginalFileName())
                .fileData(fileData)
                .size(multipartFile.getSize())
                .parent(parent)
                .build();

        fileRepository.save(file);

        updateUsage(userId, file.getSize());
    }

    @Transactional
    public ResponseEntity<Resource> download(Long itemId) throws MalformedURLException {
        File file = fileRepository.findById(itemId).orElseThrow(
                () -> new Exception404("파일을 찾을 수 없습니다.")
        );
        String uploadFileName = file.getFileData().getOriginalFileName();
        String saveFileName = file.getFileData().getSaveFileName();

        UrlResource resource = new UrlResource("file:" + dirService.getPath(saveFileName));
        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }

    @Transactional
    public void delete(Long itemId, Long userId){
        File file = fileRepository.findById(itemId).orElseThrow(
                () -> new Exception404("파일을 찾을 수 없습니다.")
        );

        fileRepository.deleteById(itemId);
        //클라우드에서 파일 진짜 삭제

        updateUsage(userId, -file.getSize());
    }

    private void updateUsage(Long userId, Long fileSize){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new Exception404("사용자를 찾을 수 없습니다.")
        );
        user.updateUsage(user.getUsage() + fileSize);
    }

    private FileData transfer(MultipartFile file){
        if(file.isEmpty()){
            throw new Exception404("파일이 없습니다.");
        }

        String originalFilename = file.getOriginalFilename();
        if(originalFilename == null){
            throw new Exception400("파일 이름이 없습니다.");
        }

        int pos = originalFilename.lastIndexOf(".");
        String ext = originalFilename.substring(pos + 1);
        String uuid = UUID.randomUUID().toString();
        String saveFileName = uuid + "." + ext;

        return new FileData(originalFilename, saveFileName);
    }
}
