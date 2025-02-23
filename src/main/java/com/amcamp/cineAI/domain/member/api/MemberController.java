package com.amcamp.cineAI.domain.member.api;

import com.amcamp.cineAI.domain.member.application.MemberService;
import com.amcamp.cineAI.domain.member.dto.request.MemberEditRequest;
import com.amcamp.cineAI.domain.member.dto.response.MemberInfoResponse;
import com.amcamp.cineAI.domain.movie.dto.response.BasicMovieInfoResponse;
import com.amcamp.cineAI.global.util.CookieUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "회원 API", description = "회원 관련 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final CookieUtil cookieUtil;
    private final MemberService memberService;

    @Operation(summary = "회원 탈퇴", description = "회원 탈퇴를 진행합니다.")
    @DeleteMapping("/withdrawal")
    public ResponseEntity<Void> memberWithdrawal() {
        memberService.withdrawalMember();
        return ResponseEntity.ok().headers(cookieUtil.deleteRefreshTokenCookie()).build();
    }

    @Operation(summary = "로그아웃", description = "로그아웃을 진행합니다.")
    @PostMapping("/logout")
    public ResponseEntity<Void> memberLogout() {
        memberService.logoutMember();
        return ResponseEntity.ok().headers(cookieUtil.deleteRefreshTokenCookie()).build();
    }

    @Operation(summary = "회원 정보 조회", description = "현재 로그인 된 회원의 정보를 조회합니다.")
    @GetMapping("/me")
    public MemberInfoResponse memberInfo() {
        return memberService.getMemberInfo();
    }

    @Operation(summary = "회원 정보 수정", description = "현재 로그인 된 회원의 정보를 조회합니다.")
    @PatchMapping("/me/edit")
    public ResponseEntity<Void> memberEdit(
            @Valid @RequestBody MemberEditRequest memberEditRequest) {
        memberService.editMemberInfo(memberEditRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/list")
    public Slice<BasicMovieInfoResponse> memberMovieLikedList(
            @Parameter(description = "이전 페이지의 마지막 영화 ID (첫 페이지는 비워두세요)")
                    @RequestParam(required = false)
                    Long lastMovieId,
            @RequestParam(value = "size", defaultValue = "3") int pageSize) {
        return memberService.getMemberLikedMovie(lastMovieId, pageSize);

    @Operation(summary = "관리자 로그인", description = "관리자용 비밀번호를 입력받아 관리자 로그인을 진행합니다.")
    @PostMapping("/admin-login")
    public ResponseEntity<String> adminLogin(@RequestParam String password) {
        if ("admin1234".equals(password)) {
            return ResponseEntity.ok("로그인 성공");
        } else {
            return ResponseEntity.status(401).body("비밀번호가 잘못되었습니다.");
        }

    }
}
