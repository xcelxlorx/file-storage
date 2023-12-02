package com.gihae.filestorage.service;

import com.gihae.filestorage.domain.FileData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {

    void upload(FileData fileData, MultipartFile file) throws IOException;

    ResponseEntity<?> download(String originalFileName, String saveFileName) throws IOException;

    void delete(String saveFileName);

    String getPath(String fileName);
}
