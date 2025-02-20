package com.amcamp.cineAI.domain.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MemberInfoResponse(String nickName, String profileImageUrl, String email) {
    public static MemberInfoResponse of(String nickName, String profileImageUrl, String email) {
        return new MemberInfoResponse(nickName, profileImageUrl, email);
    }
}
