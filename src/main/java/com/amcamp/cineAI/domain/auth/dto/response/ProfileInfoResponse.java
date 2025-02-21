package com.amcamp.cineAI.domain.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ProfileInfoResponse(String nickName, String profileImageUrl, String email) {
    public static ProfileInfoResponse of(KakaoUserInfoResponseDto userInfo) {
        return new ProfileInfoResponse(
                userInfo.getKakaoAccount().getProfile().nickName,
                userInfo.getKakaoAccount().getProfile().profileImageUrl,
                userInfo.getKakaoAccount().email);
    }
}
