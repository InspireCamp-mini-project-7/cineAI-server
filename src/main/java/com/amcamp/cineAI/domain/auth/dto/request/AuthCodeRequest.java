package com.amcamp.cineAI.domain.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record AuthCodeRequest(
        @NotBlank(message = "인증 코드는 필수 항목입니다")
                @Schema(description = "카카오 로그인을 위한 인증 코드, redirect-url의 code=부터 끝까지입니다.")
                String code) {}
