package com.gihae.filestorage._core.errors.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 사용자를 찾을 수 없습니다."),

    FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 파일을 찾을 수 없습니다."),
    FILE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다."),
    FILE_DOWNLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "파일 다운로드에 실패했습니다."),
    FILE_NAME_IS_EMPTY(HttpStatus.BAD_REQUEST, "파일 이름이 없습니다."),

    PARENT_FOLDER_NOT_FOUND(HttpStatus.NOT_FOUND, "상위 폴더를 찾을 수 없습니다.");

    private final HttpStatus status;
    private final String message;

}
