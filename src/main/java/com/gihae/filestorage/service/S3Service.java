package com.gihae.filestorage.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.gihae.filestorage.domain.FileData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Service implements StorageService{

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;



    @Override
    public void upload(FileData fileData, MultipartFile file) throws IOException {
        String fileName = fileData.getSaveFileName();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());

        try (InputStream inputStream = file.getInputStream()) {
            amazonS3.putObject(bucket, fileName, inputStream, metadata);
        }
    }

    @Override
    public ResponseEntity<byte[]> download(String originalFileName, String saveFileName) throws IOException{
        S3Object object = amazonS3.getObject(new GetObjectRequest(bucket, saveFileName));

        S3ObjectInputStream objectContent = object.getObjectContent();
        byte[] bytes = IOUtils.toByteArray(objectContent);
        String encodedOriginalFileName = UriUtils.encode(originalFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedOriginalFileName + "\"";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(bytes);
    }

    @Override
    public void delete(String saveFileName){
        amazonS3.deleteObject(bucket, saveFileName);
    }

    @Override
    public String getPath(String fileName){
        return amazonS3.getUrl(bucket, fileName).toString();
    }
}