package com.amcamp.cineAI.domain.auth.dto.response;

import com.amcamp.cineAI.domain.auth.domain.LoginStatus;
import io.swagger.v3.oas.annotations.media.Schema;

public record FirstLoginResponse(
        @Schema(description = "권한 인증에 필요한 액세스토큰") String accessToken,
        @Schema(description = "쿠키에 전달되는 리프레시 토큰") String refreshToken,
        @Schema(description = "최초 로그인 판별") LoginStatus loginStatus) {
    public static FirstLoginResponse of(
            SocialLoginResponse socialLoginResponse, LoginStatus loginStatus) {
        return new FirstLoginResponse(
                socialLoginResponse.accessToken(), socialLoginResponse.refreshToken(), loginStatus);
    }
}
