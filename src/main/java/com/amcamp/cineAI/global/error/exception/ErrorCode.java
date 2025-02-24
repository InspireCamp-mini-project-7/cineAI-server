package com.amcamp.cineAI.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),
    MEMBER_ALREADY_DELETED(HttpStatus.BAD_REQUEST, "이미 탈퇴한 회원입니다."),
    AUTH_NOT_FOUND(HttpStatus.UNAUTHORIZED, "사용자 인증 정보를 찾을 수 없습니다. 올바른 토큰으로 요청해주세요."),
    MOVIE_NOT_FOUND(HttpStatus.NOT_FOUND, "영화를 찾을 수 없습니다."),
    CSV_NOT_UPLOAD(HttpStatus.INTERNAL_SERVER_ERROR, "업로드에 실패했습니다."),
    SEARCH_KEYWORD_NOT_FOUND(HttpStatus.BAD_REQUEST, "검색어가 생성되지 않았습니다.");
    POSTER_NOT_FOUND(HttpStatus.NOT_FOUND, "포스터를 찾을 수 없습니다."),
    FILE_UPLOAD_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "파일 저장에 실패했습니다."),
    ADMIN_NOT_FOUND(HttpStatus.NOT_FOUND, "관리자 비밀번호가 일치하지 않습니다.");

    private final HttpStatus status;
    private final String message;
}
