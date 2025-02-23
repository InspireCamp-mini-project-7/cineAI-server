package com.amcamp.cineAI.domain.auth.application;

import com.amcamp.cineAI.domain.auth.dao.RefreshTokenRepository;
import com.amcamp.cineAI.domain.auth.domain.LoginStatus;
import com.amcamp.cineAI.domain.auth.domain.RefreshToken;
import com.amcamp.cineAI.domain.auth.dto.request.AuthCodeRequest;
import com.amcamp.cineAI.domain.auth.dto.response.FirstLoginResponse;
import com.amcamp.cineAI.domain.auth.dto.response.ProfileInfoResponse;
import com.amcamp.cineAI.domain.auth.dto.response.SocialLoginResponse;
import com.amcamp.cineAI.domain.member.dao.MemberRepository;
import com.amcamp.cineAI.domain.member.domain.Member;
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
    private final RefreshTokenRepository refreshTokenRepository;
    private final KakaoService kakaoService;

    public FirstLoginResponse oAuthLogin(AuthCodeRequest authCodeRequest) {

        // 카카오 또는 구글로부터 코드값으로 토큰값 반환 - 토큰으로 유저 정보 획득
        SocialLoginResponse response = kakaoService.getSocialLoginResponse(authCodeRequest.code());
        String accessToken = response.accessToken();
        ProfileInfoResponse profileInfoResponse = kakaoService.getProfileInfo(accessToken);
        String email = profileInfoResponse.email();
        boolean isFirstLogin =
                memberRepository.findByEmail(email).isEmpty(); // empty면 true 아니면 false

        // 획득한 정보로 회원 상태 확인, 없으면 가입, 있으면 토큰값만 반환
        Member member =
                memberRepository
                        .findByEmail(email)
                        .orElseGet(() -> saveMember(profileInfoResponse));
        LoginStatus loginStatus = isFirstLogin ? LoginStatus.FIRST : LoginStatus.NOTFIRST;

        RefreshToken refreshToken =
                RefreshToken.createRefreshToken(member.getId(), response.refreshToken());
        refreshTokenRepository.save(refreshToken);

        FirstLoginResponse firstLoginResponse = FirstLoginResponse.of(response, loginStatus);
        return firstLoginResponse;
    }

    private Member saveMember(ProfileInfoResponse profileInfoResponse) {
        Member user =
                Member.createMember(
                        profileInfoResponse.nickName(),
                        profileInfoResponse.profileImageUrl(),
                        profileInfoResponse.email());
        memberRepository.save(user);
        return user;
    }
}
