package com.gihae.filestorage._core.errors.exception;

import com.gihae.filestorage._core.utils.ApiUtils;
import org.springframework.http.HttpStatus;

public class Exception500 extends RuntimeException{

    public Exception500(String message) {
        super(message);
    }

    public ApiUtils.ApiResult<?> body(){
        return ApiUtils.error(getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public HttpStatus status(){
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
