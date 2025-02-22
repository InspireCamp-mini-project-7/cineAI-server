package com.amcamp.cineAI.domain.member.application;

import com.amcamp.cineAI.domain.auth.dao.RefreshTokenRepository;
import com.amcamp.cineAI.domain.auth.domain.RefreshToken;
import com.amcamp.cineAI.domain.member.dao.MemberRepository;
import com.amcamp.cineAI.domain.member.domain.Member;
import com.amcamp.cineAI.domain.member.dto.request.MemberEditRequest;
import com.amcamp.cineAI.domain.member.dto.response.MemberInfoResponse;
import com.amcamp.cineAI.global.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberUtil memberUtil;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;

    public void logoutMember() {
        Member currentMember = memberUtil.getCurrentMember();
        RefreshToken refreshToken = refreshTokenRepository.findByMemberId(currentMember.getId());
        refreshTokenRepository.delete(refreshToken);
    }

    public void withdrawalMember() {
        Member currentMember = memberUtil.getCurrentMember();
        refreshTokenRepository
                .findById(currentMember.getId())
                .ifPresent(refreshTokenRepository::delete);

        currentMember.withdrawal();
    }

    @Transactional(readOnly = true)
    public MemberInfoResponse getMemberInfo() {
        Member currentMember = memberUtil.getCurrentMember();
        return MemberInfoResponse.of(currentMember);
    }

    public MemberInfoResponse editMemberInfo(MemberEditRequest memberEditRequest) {
        Member currentMember = memberUtil.getCurrentMember();
        currentMember.updateNickname(memberEditRequest.nickname());
        return MemberInfoResponse.of(currentMember);
    }

    //    @Transactional(readOnly = true)
    //    public Slice<BasicMovieInfoResponse> getMemberLikedMovie(Long lastMovieId, int pageSize) {
    //
    //    }
}
