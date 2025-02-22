package com.amcamp.cineAI.domain.llm.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RankingRequest {
    @NotBlank(message = "영화 목록은 필수 항목입니다")
    @Schema(
            description = "검색된 영화 목록 (쉼표로 구분된 영화 제목)",
            example = "인셉션, 인터스텔라, 다크 나이트, 매드 맥스: 분노의 도로")
    private String movieList;

    @Schema(
            description = "사용자 선호 정보 (선택 사항)",
            example =
                    "본 영화: 인셉션, 다크 나이트; 싫은 영화: 매드 맥스: 분노의 도로; 선호 장르: SF, 액션; 좋아하는 배우: 레오나르도 디카프리오; 좋아하는 감독: 크리스토퍼 놀란")
    private String userPreference;
}
