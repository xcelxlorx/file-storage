package com.gihae.filestorage.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class UserRequest {

    @Getter
    @Setter
    public static class LoginDTO{

        private String email;
        private String password;
    }

    @Getter
    @Setter
    public static class JoinDTO{

        private String username;
        private String email;
        private String password;
    }
}
