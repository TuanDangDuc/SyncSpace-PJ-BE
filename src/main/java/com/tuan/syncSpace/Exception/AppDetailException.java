package com.tuan.syncSpace.Exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AppDetailException extends RuntimeException{
    private final HttpStatus ErrorCode;
    private final Object detailMessage;

    public AppDetailException(HttpStatus errorCode, Object detailMessage, String message) {
        super("APP");
        this.ErrorCode = errorCode;
        this.detailMessage = detailMessage;
    }
}
