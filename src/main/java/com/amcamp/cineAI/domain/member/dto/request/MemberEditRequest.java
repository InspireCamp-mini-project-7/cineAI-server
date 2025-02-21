package com.amcamp.cineAI.domain.member.dto.request;

import jakarta.validation.constraints.NotBlank;

public record MemberEditRequest(@NotBlank(message = "닉네임은 비워둘 수 없습니다.") String nickname) {}
