package com.amcamp.cineAI.domain.member.dto.response;

import com.amcamp.cineAI.domain.member.domain.Member;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MemberInfoResponse(String nickName, String profileImageUrl, String email) {
    public static MemberInfoResponse of(Member member) {
        return new MemberInfoResponse(
                member.getNickname(), member.getProfileImageUrl(), member.getEmail());
    }
}
