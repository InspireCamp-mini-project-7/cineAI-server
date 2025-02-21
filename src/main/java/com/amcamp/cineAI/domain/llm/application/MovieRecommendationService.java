package com.amcamp.cineAI.domain.llm.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieRecommendationService {

    private final PromptService promptService;
    private final LLMService llmService;

    public String getRankingResult(String movieList, String userPreference) {
        // 하드코딩된 임시 데이터 사용
        movieList = "인셉션, 인터스텔라, 다크 나이트, 매드 맥스: 분노의 도로, 조커";
        userPreference =
                "본 영화: 인셉션, 다크 나이트; 싫은 영화: 매드 맥스: 분노의 도로; 선호 장르: SF, 액션; 좋아하는 배우: 레오나르도 디카프리오; 좋아하는 감독: 크리스토퍼 놀란";

        if (movieList == null || movieList.trim().isEmpty()) {
            return "검색된 영화가 없습니다";
        }
        String prompt = promptService.getMovieRankingPrompt(movieList, userPreference);
        return llmService.callLLM(prompt);
    }
}
