package com.amcamp.cineAI.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    TEST_ERROR(HttpStatus.BAD_REQUEST, "Error For Test");

    private final HttpStatus status;
    private final String message;
}
