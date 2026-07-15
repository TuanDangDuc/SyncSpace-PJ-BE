package com.tuan.syncSpace.Exception;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@RequiredArgsConstructor
@Getter
public class AppException extends RuntimeException {
    private final HttpStatus errorCode;

    @Builder
    public AppException(HttpStatus errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
