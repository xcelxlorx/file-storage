package com.gihae.filestorage.service;

import com.gihae.filestorage._core.errors.exception.Exception400;
import com.gihae.filestorage._core.errors.exception.Exception404;
import com.gihae.filestorage.domain.SaveFile;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class DirService {

    @Value("${file.dir}")
    private String fileDir;

    public String getPath(String saveFileName) {
        return fileDir + saveFileName;
    }

    public SaveFile transfer(MultipartFile file) throws IOException {
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

        file.transferTo(new java.io.File(getPath(saveFileName)));

        return new SaveFile(originalFilename, saveFileName);
    }
}