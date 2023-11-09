package com.gihae.filestorage.service;

import com.gihae.filestorage.controller.dto.FileRequest;
import com.gihae.filestorage.controller.dto.FileResponse;
import com.gihae.filestorage.domain.SaveFile;
import com.gihae.filestorage._core.errors.exception.Exception400;
import com.gihae.filestorage._core.errors.exception.Exception404;
import com.gihae.filestorage.domain.File;
import com.gihae.filestorage.domain.Folder;
import com.gihae.filestorage.repository.FileRepository;
import com.gihae.filestorage.repository.FolderRepository;
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
import java.util.Optional;
import java.util.UUID;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class FileService {

    private final FileRepository fileRepository;
    private final FolderRepository folderRepository;

    private final UserService userService;
    private final S3Service s3Service;
    private final DirService dirService;

    public FileResponse.FindFileDTO findByFolderId(Long folderId){
        List<File> files = fileRepository.findByFolderId(folderId);
        return new FileResponse.FindFileDTO(files);
    }

    @Transactional
    public void upload(FileRequest.UploadDTO uploadDTO, Long folderId) throws IOException {
        Optional<File> findFile = fileRepository.findByName(uploadDTO.getFile().getOriginalFilename());
        if(findFile.isPresent()){
            throw new Exception400("동일한 이름의 파일이 존재합니다.");
        }

        Folder parent = folderRepository.findById(folderId).orElseThrow(
                () -> new Exception404("해당 폴더가 존재하지 않습니다.")
        );

        SaveFile saveFile = dirService.transfer(uploadDTO.getFile());

        File file = File.builder()
                .name(saveFile.getOriginalFileName())
                .file(saveFile)
                .size(uploadDTO.getFile().getSize())
                .parent(parent)
                .build();
        fileRepository.save(file);

        userService.updateUsage(1L, file.getSize());
    }

    @Transactional
    public ResponseEntity<Resource> download(Long itemId) throws MalformedURLException {
        File file = fileRepository.findById(itemId).orElseThrow(
                () -> new Exception404("파일을 찾을 수 없습니다.")
        );
        String uploadFileName = file.getFile().getOriginalFileName();
        String saveFileName = file.getFile().getSaveFileName();

        UrlResource resource = new UrlResource("file:" + dirService.getPath(saveFileName));
        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }

    @Transactional
    public void delete(Long itemId){
        File file = fileRepository.findById(itemId).orElseThrow(
                () -> new Exception404("파일을 찾을 수 없습니다.")
        );

        userService.updateUsage(1L, -file.getSize());
        fileRepository.deleteById(itemId);

        //클라우드에서 파일 진짜 삭제
    }
}
