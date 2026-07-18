package com.tuan.syncSpace.Exception;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;


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
