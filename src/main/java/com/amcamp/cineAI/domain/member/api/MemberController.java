package com.amcamp.cineAI.domain.member.api;

import com.amcamp.cineAI.domain.member.application.MemberService;
import com.amcamp.cineAI.domain.member.dto.response.MemberInfoResponse;
import com.amcamp.cineAI.global.util.CookieUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "회원 API", description = "회원 관련 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final CookieUtil cookieUtil;
    private final MemberService memberService;

    @DeleteMapping("/withdrawal")
    public ResponseEntity<Void> memberWithdrawal() {
        memberService.withdrawalMember();
        return ResponseEntity.ok().headers(cookieUtil.deleteRefreshTokenCookie()).build();
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> memberLogout() {
        memberService.logoutMember();
        return ResponseEntity.ok().headers(cookieUtil.deleteRefreshTokenCookie()).build();
    }

    @GetMapping("/me")
    public MemberInfoResponse memberInfo() {
        return memberService.getMemberInfo();
    }
}
