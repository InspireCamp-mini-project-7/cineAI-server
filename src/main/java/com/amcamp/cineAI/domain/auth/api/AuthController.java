package com.amcamp.cineAI.domain.auth.api;

import com.amcamp.cineAI.domain.auth.application.AuthService;
import com.amcamp.cineAI.domain.auth.dto.request.AuthCodeRequest;
import com.amcamp.cineAI.domain.auth.dto.response.SocialLoginResponse;
import com.amcamp.cineAI.global.util.CookieUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "인증 API", description = "인증 관련 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final CookieUtil cookieUtil;

    @Operation(summary = "회원가입 및 로그인", description = "회원가입 및 로그인을 진행합니다.")
    @PostMapping("/social-login")
    public ResponseEntity<SocialLoginResponse> memberSocialLogin(
            @Valid @RequestBody AuthCodeRequest authCodeRequest) {
        SocialLoginResponse socialLoginResponse = authService.oAuthLogin(authCodeRequest);
        String refreshToken = socialLoginResponse.refreshToken();
        HttpHeaders headers = cookieUtil.generateRefreshTokenCookie(refreshToken);
        return ResponseEntity.ok().headers(headers).body(socialLoginResponse);
    }
}
