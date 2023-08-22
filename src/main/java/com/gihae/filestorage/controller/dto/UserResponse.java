package com.gihae.filestorage.controller.dto;

import com.gihae.filestorage.domain.Folder;
import com.gihae.filestorage.domain.User;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class UserResponse {

    @Getter
    public static class FindUserDTO{

        private final String username;
        private final String email;
        private final Long usage;

        public FindUserDTO(User user) {
            this.username = user.getUsername();
            this.email = user.getEmail();
            this.usage = user.getUsage();
        }
    }
}
