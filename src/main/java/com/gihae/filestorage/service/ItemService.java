package com.gihae.filestorage.service;

import com.gihae.filestorage.controller.dto.ItemRequest;
import com.gihae.filestorage.controller.dto.ItemResponse;
import com.gihae.filestorage.controller.dto.SaveFile;
import com.gihae.filestorage.domain.Item;
import com.gihae.filestorage.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ItemService {

    @Value("${file.dir}")
    private String fileDir;

    private final ItemRepository itemRepository;

    public ItemResponse.FindItemDTO findAll(){
        List<Item> items = itemRepository.findAll();
        return new ItemResponse.FindItemDTO(items);
    }

    @Transactional
    public void upload(ItemRequest.UploadDTO uploadDTO) throws IOException {
        SaveFile file = transfer(uploadDTO.getFile());

        Item item = Item.builder()
                .name(uploadDTO.getName())
                .file(file)
                .build();
        itemRepository.save(item);
    }

    private SaveFile transfer(MultipartFile file) throws IOException {
        if(file.isEmpty()){
            return null;
        }

        String originalFilename = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        int pos = originalFilename.lastIndexOf(".");
        String ext = originalFilename.substring(pos + 1);
        String saveFileName = uuid + "." + ext;
        file.transferTo(new File(getPath(saveFileName)));
        return new SaveFile(originalFilename, saveFileName);
    }

    private String getPath(String saveFileName) {
        return fileDir + saveFileName;
    }

    @Transactional
    public ResponseEntity<Resource> download(Long itemId) throws MalformedURLException {
        Item item = itemRepository.findById(itemId).get();
        String uploadFileName = item.getFile().getUploadFileName();
        String saveFileName = item.getFile().getSaveFileName();

        UrlResource resource = new UrlResource("file:" + getPath(saveFileName));
        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }

    @Transactional
    public void delete(Long itemId){
        itemRepository.deleteById(itemId);

        //클라우드에서 파일 진짜 삭제
    }
}
