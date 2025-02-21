package com.amcamp.cineAI.domain.llm.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QARequest {
    @NotBlank(message = "질문은 필수 항목입니다")
    @Schema(description = "영화 관련 질문", example = "이 영화의 주제는 무엇인가요?")
    private String question;
}
