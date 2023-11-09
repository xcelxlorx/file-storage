package com.gihae.filestorage.controller;

import com.gihae.filestorage.controller.dto.FolderResponse;
import com.gihae.filestorage.controller.dto.FileRequest;
import com.gihae.filestorage.controller.dto.FileResponse;
import com.gihae.filestorage.service.FolderService;
import com.gihae.filestorage.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.net.MalformedURLException;

@Slf4j
@RequiredArgsConstructor
@Controller
public class FileController {

    private final FileService fileService;
    private final FolderService folderService;

    @GetMapping
    String items(Model model){
        FileResponse.FindFileDTO files = fileService.findByFolderId(0L);
        FolderResponse.FindFolderDTO folders = folderService.findByParentId(0L);
        model.addAttribute("currentId", 0L);
        model.addAttribute("files", files);
        model.addAttribute("folders", folders);
        return "index";
    }

    @GetMapping("/{currentId}/upload")
    String uploadForm(Model model){
        model.addAttribute("UploadDTO", new FileRequest.UploadDTO());
        return "upload-form";
    }

    @PostMapping("/{currentId}/upload")
    public String upload(@PathVariable Long currentId, @ModelAttribute FileRequest.UploadDTO file) throws IOException {
        Long userId = 1L;
        fileService.upload(file, currentId, userId);
        return "redirect:/";
    }

    @GetMapping("/files/{fileId}/download")
    public ResponseEntity<Resource> download(@PathVariable Long fileId) throws MalformedURLException {
        return fileService.download(fileId);
    }

    @GetMapping("/files/{fileId}/delete")
    public String delete(@PathVariable Long fileId){
        Long userId = 1L;
        fileService.delete(fileId, userId);
        return "redirect:/";
    }
}
