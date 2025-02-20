package com.amcamp.cineAI.domain.auth.application;

import static com.amcamp.cineAI.global.common.constants.SecurityConstants.KAKAO_LOGIN_URL;
import static com.amcamp.cineAI.global.common.constants.SecurityConstants.KAKAO_USER_URL;

import com.amcamp.cineAI.domain.auth.dto.response.KakaoTokenResponseDto;
import com.amcamp.cineAI.domain.auth.dto.response.KakaoUserInfoResponseDto;
import com.amcamp.cineAI.domain.auth.dto.response.MemberInfoResponse;
import com.amcamp.cineAI.domain.auth.dto.response.SocialLoginResponse;
import io.netty.handler.codec.http.HttpHeaderValues;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoService {
    @Value("${kakao.client_id}")
    private String clientId;

    @Value("${kakao.redirect_uri}")
    private String redirectUri;

    public SocialLoginResponse getSocialLoginResponse(String code) {

        KakaoTokenResponseDto kakaoTokenResponseDto =
                WebClient.create(KAKAO_LOGIN_URL)
                        .post()
                        .uri("/oauth/token")
                        .header(
                                HttpHeaders.CONTENT_TYPE,
                                HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                        .body(
                                BodyInserters.fromFormData("grant_type", "authorization_code")
                                        .with("client_id", clientId)
                                        .with("code", code)
                                        .with("redirect_uri", redirectUri))
                        .retrieve()
                        .onStatus(
                                clientResponse -> clientResponse.is4xxClientError(),
                                clientResponse ->
                                        Mono.error(new RuntimeException("Invalid Parameter")))
                        .onStatus(
                                clientResponse -> clientResponse.is5xxServerError(),
                                clientResponse ->
                                        Mono.error(new RuntimeException("Internal Server Error")))
                        .bodyToMono(KakaoTokenResponseDto.class)
                        .block();
        return SocialLoginResponse.of(
                kakaoTokenResponseDto.getAccessToken(), kakaoTokenResponseDto.getRefreshToken());
    }

    public MemberInfoResponse getMemberInfo(String accessToken) {

        KakaoUserInfoResponseDto userInfo =
                WebClient.create(KAKAO_USER_URL)
                        .get()
                        .uri(
                                uriBuilder ->
                                        uriBuilder.scheme("https").path("/v2/user/me").build(true))
                        .header(
                                HttpHeaders.AUTHORIZATION,
                                "Bearer " + accessToken) // access token 인가
                        .header(
                                HttpHeaders.CONTENT_TYPE,
                                HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                        .retrieve()
                        .onStatus(
                                clientResponse -> clientResponse.is4xxClientError(),
                                clientResponse ->
                                        Mono.error(new RuntimeException("Invalid Parameter")))
                        .onStatus(
                                clientResponse -> clientResponse.is5xxServerError(),
                                clientResponse ->
                                        Mono.error(new RuntimeException("Internal Server Error")))
                        .bodyToMono(KakaoUserInfoResponseDto.class)
                        .block();

        return MemberInfoResponse.of(
                userInfo.getKakaoAccount().getProfile().nickName,
                userInfo.getKakaoAccount().getProfile().profileImageUrl,
                userInfo.getKakaoAccount().email);
    }
}
