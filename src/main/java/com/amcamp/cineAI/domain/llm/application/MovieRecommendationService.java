package com.amcamp.cineAI.domain.llm.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieRecommendationService {

    private final PromptService promptService;
    private final LLMService llmService;

    @Autowired
    public MovieRecommendationService(PromptService promptService, LLMService llmService) {
        this.promptService = promptService;
        this.llmService = llmService;
    }

    public String getRankingResult(String movieList, String userPreference) {
        // 하드코딩된 임시 데이터 사용
        movieList = "인셉션, 인터스텔라, 다크 나이트, 매드 맥스: 분노의 도로, 조커";
        userPreference =
                "본 영화: 인셉션, 다크 나이트; 싫은 영화: 매드 맥스: 분노의 도로; 선호 장르: SF, 액션; 좋아하는 배우: 레오나르도 디카프리오; 좋아하는 감독: 크리스토퍼 놀란";

        // 영화 목록이 비어 있으면 바로 오류 메시지 반환
        if (movieList == null || movieList.trim().isEmpty()) {
            return "검색된 영화가 없습니다";
        }
        String prompt = promptService.getMovieRankingPrompt(movieList, userPreference);
        return llmService.callLLM(prompt);
    }
}
