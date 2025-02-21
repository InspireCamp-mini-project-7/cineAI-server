package com.amcamp.cineAI.domain.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ProfileInfoResponse(
        @Schema(description = "회원 닉네임", example = "조수빈") String nickName,
        @Schema(
                        description = "회원 프로필 이미지 주소",
                        example =
                                "http://img1.kakaocdn.net/thumb/R640x640.q70/?fname=http://t1.kakaocdn.net/account_images/default_profile.jpeg")
                String profileImageUrl,
        @Schema(description = "회원 이메일", example = "example@example.com") String email) {
    public static ProfileInfoResponse of(KakaoUserInfoResponseDto userInfo) {
        return new ProfileInfoResponse(
                userInfo.getKakaoAccount().getProfile().nickName,
                userInfo.getKakaoAccount().getProfile().profileImageUrl,
                userInfo.getKakaoAccount().email);
    }
}
