package com.gihae.filestorage.service;

import com.gihae.filestorage.domain.FileData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
@Service
public class DirService {

    @Value("${file.dir}")
    private String fileDir;

    public String getPath(String saveFileName) {
        return fileDir + saveFileName;
    }

    public void upload(FileData fileData, MultipartFile file) throws IOException {
        file.transferTo(new File(getPath(fileData.getSaveFileName()))); //서버의 파일 시스템에 저장
    }

    public ResponseEntity<Resource> download(String originalFileName, String saveFileName) throws MalformedURLException {
        UrlResource resource = new UrlResource("file:" + getPath(saveFileName));
        String encodedOriginalFileName = UriUtils.encode(originalFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedOriginalFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }
}