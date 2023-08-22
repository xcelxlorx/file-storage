package com.gihae.filestorage.controller;

import com.gihae.filestorage.controller.dto.FolderRequest;
import com.gihae.filestorage.controller.dto.FolderResponse;
import com.gihae.filestorage.controller.dto.FileResponse;
import com.gihae.filestorage.service.FolderService;
import com.gihae.filestorage.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/folders")
@RequiredArgsConstructor
@Controller
public class FolderController {

    private final FolderService folderService;
    private final FileService fileService;

    @GetMapping("/{folderId}")
    public String findById(@PathVariable Long folderId, Model model){
        FileResponse.FindFileDTO files = fileService.findByFolderId(folderId);
        FolderResponse.FindFolderDTO folders = folderService.findByParentId(folderId);
        model.addAttribute("currentId", folderId);
        model.addAttribute("files", files);
        model.addAttribute("folders", folders);
        return "index";
    }

    @PostMapping("/{folderId}/download")
    public void download(@PathVariable Long folderId){

    }

    @GetMapping("/{folderId}/create")
    public String createForm(Model model){
        model.addAttribute("saveDTO", new FolderRequest.SaveDTO());
        return "create";
    }

    @PostMapping("/{folderId}/create")
    public String create(@PathVariable Long folderId, @ModelAttribute FolderRequest.SaveDTO saveDTO){
        folderService.create(saveDTO, folderId);
        return "redirect:/";
    }

    @PostMapping("/{folderId}/delete")
    public String delete(@PathVariable Long folderId){
        folderService.delete(folderId);
        return "redirect:/";
    }
}
