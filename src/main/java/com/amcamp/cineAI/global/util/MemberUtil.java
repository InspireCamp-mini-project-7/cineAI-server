package com.amcamp.cineAI.global.util;

import com.amcamp.cineAI.domain.auth.dao.RefreshTokenRepository;
import com.amcamp.cineAI.domain.member.dao.MemberRepository;
import com.amcamp.cineAI.domain.member.domain.Member;
import com.amcamp.cineAI.domain.member.domain.MemberStatus;
import com.amcamp.cineAI.global.error.exception.CustomException;
import com.amcamp.cineAI.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberUtil {
    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final CookieUtil cookieUtil;

    public Member getCurrentMember() {
        Member member =
                memberRepository
                        .findById(getCurrentMemberId())
                        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        if (member.getStatus() == MemberStatus.DELETED) {
            throw new CustomException(ErrorCode.MEMBER_ALREADY_DELETED);
        }
        return member;
    }

    private Long getCurrentMemberId() {

        String token = cookieUtil.getRefreshTokenFromCookie();

        if (token == null) {
            throw new IllegalArgumentException("유효하지 않은 토큰입니다.");
        }
        Long currentMemberId = refreshTokenRepository.findByToken(token).getMemberId();
        return currentMemberId;
    }
}
