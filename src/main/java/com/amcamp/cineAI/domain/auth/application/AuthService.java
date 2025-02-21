package com.amcamp.cineAI.domain.auth.application;

import com.amcamp.cineAI.domain.auth.dto.request.AuthCodeRequest;
import com.amcamp.cineAI.domain.auth.dto.response.MemberInfoResponse;
import com.amcamp.cineAI.domain.auth.dto.response.SocialLoginResponse;
import com.amcamp.cineAI.domain.user.dao.MemberRepository;
import com.amcamp.cineAI.domain.user.domain.Member;
import com.amcamp.cineAI.domain.user.domain.MemberStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final MemberRepository memberRepository;
    private final KakaoService kakaoService;

    public SocialLoginResponse oAuthLogin(AuthCodeRequest authCodeRequest) {

        // 카카오 또는 구글로부터 코드값으로 토큰값 반환 - 토큰으로 유저 정보 획득
        SocialLoginResponse response = kakaoService.getSocialLoginResponse(authCodeRequest.code());
        String accessToken = response.accessToken();
        MemberInfoResponse memberInfoResponse = kakaoService.getMemberInfo(accessToken);
        String email = memberInfoResponse.email();

        // 획득한 정보로 회원 상태 확인, 없으면 가입, 있으면 토큰값만 반환
        Member member =
                memberRepository.findByEmail(email).orElseGet(() -> saveMember(memberInfoResponse));

        if (member.getStatus() == MemberStatus.DELETED) {
            member.reEnroll();
        }
        return response;
    }

    private Member saveMember(MemberInfoResponse memberInfoResponse) {
        Member user =
                Member.createMember(
                        memberInfoResponse.nickName(),
                        memberInfoResponse.profileImageUrl(),
                        memberInfoResponse.email());
        memberRepository.save(user);
        return user;
    }
}
