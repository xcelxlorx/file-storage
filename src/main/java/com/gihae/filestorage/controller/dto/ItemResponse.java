package com.gihae.filestorage.controller.dto;

import com.gihae.filestorage.domain.Item;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class ItemResponse {

    @Getter
    public static class FindItemDTO{

        List<ItemDTO> items;

        public FindItemDTO(List<Item> items) {
            this.items = items.stream().map(ItemDTO::new).collect(Collectors.toList());
        }

        @Getter
        public class ItemDTO{

            private final Long id;
            private final String name;
            private final SaveFile file;
            private final Long size;

            public ItemDTO(Item item) {
                this.id = item.getId();
                this.name = item.getName();
                this.file = item.getFile();
                this.size = item.getSize();
            }
        }
    }
}
