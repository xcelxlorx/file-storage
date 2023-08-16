package com.gihae.filestorage.controller;

import com.gihae.filestorage.controller.dto.ItemRequest;
import com.gihae.filestorage.controller.dto.ItemResponse;
import com.gihae.filestorage.service.ItemService;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
@Controller
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items")
    String items(Model model){
        ItemResponse.FindItemDTO items = itemService.findAll();
        model.addAttribute("items", items);
        return "items";
    }

    @GetMapping("/upload")
    String uploadForm(Model model){
        model.addAttribute("item", new ItemRequest.UploadDTO());
        return "upload-form";
    }

    @PostMapping("/upload")
    public String upload(@ModelAttribute ItemRequest.UploadDTO item) throws IOException {
        itemService.upload(item);
        return "redirect:/";
    }

    @GetMapping("/items/{itemId}/download")
    public ResponseEntity<Resource> download(@PathVariable Long itemId) throws MalformedURLException {
        return itemService.download(itemId);
    }

    @GetMapping("/items/{itemId}/delete")
    public String delete(@PathVariable Long itemId){
        itemService.delete(itemId);
        return "redirect:/items";
    }
}
