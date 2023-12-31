package com.gihae.filestorage.controller;

import com.gihae.filestorage._core.security.CustomUserDetails;
import com.gihae.filestorage.controller.dto.FileRequest;
import com.gihae.filestorage.controller.dto.FileResponse;
import com.gihae.filestorage.controller.dto.FolderResponse;
import com.gihae.filestorage.service.FileService;
import com.gihae.filestorage.service.FolderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class FileController {

    private final FileService fileService;
    private final FolderService folderService;

    @GetMapping
    String items(Model model, @AuthenticationPrincipal CustomUserDetails userDetails){
        if(userDetails == null || userDetails.getUser() == null){
            return "landing";
        }else{
            Long folderId = 1L;
            FileResponse.FindFileDTO files = fileService.findByFolderId(folderId);
            FolderResponse.FindFolderDTO folders = folderService.findByParentId(folderId);
            model.addAttribute("folderId", folderId);
            model.addAttribute("files", files);
            model.addAttribute("folders", folders);
            return "home";
        }
    }

    @GetMapping("/folders/{folderId}/upload")
    String uploadForm(Model model){
        model.addAttribute("UploadDTO", new FileRequest.UploadDTO());
        return "upload-form";
    }

    @PostMapping("/folders/{folderId}/upload")
    public String upload(@PathVariable Long folderId,
                         @ModelAttribute FileRequest.UploadDTO file,
                         @AuthenticationPrincipal CustomUserDetails userDetails) {
        fileService.upload(file, folderId, userDetails.getUser().getId());
        return "redirect:/";
    }

    @GetMapping("/files/{fileId}/download")
    public ResponseEntity<?> download(@PathVariable Long fileId) {
        return fileService.download(fileId);
    }

    @GetMapping("/files/{fileId}/delete")
    public String delete(@PathVariable Long fileId, @AuthenticationPrincipal CustomUserDetails userDetails){
        fileService.delete(fileId, userDetails.getUser().getId());
        return "redirect:/";
    }
}
