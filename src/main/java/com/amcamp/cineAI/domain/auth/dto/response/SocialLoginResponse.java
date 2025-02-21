package com.amcamp.cineAI.domain.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record SocialLoginResponse(
        @Schema(description = "권한 인증에 필요한 액세스토큰") String accessToken,
        @Schema(description = "쿠키에 전달되는 리프레시 토큰") String refreshToken) {
    public static SocialLoginResponse of(String accessToken, String refreshToken) {
        return new SocialLoginResponse(accessToken, refreshToken);
    }
}
