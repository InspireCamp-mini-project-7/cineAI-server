package com.amcamp.cineAI.domain.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record MemberEditRequest(
        @NotBlank(message = "닉네임은 비워둘 수 없습니다.") @Schema(description = "회원 닉네임", example = "조수빈")
                String nickname) {}
