package com.gihae.filestorage._core.errors.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException{

    private final ExceptionCode error;

    public ApiException(ExceptionCode error) {
        super(error.getMessage());
        this.error = error;
    }
}
