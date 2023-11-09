package com.gihae.filestorage.service;

import com.gihae.filestorage.domain.FileData;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

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
}