// 기존 package 및 import 구문 유지
package com.amcamp.cineAI.domain.llm.application;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

@Service
public class PromptService {

    // 기존 영화 추천 프롬프트 메서드 (movie-ranking.txt)
    public String getMovieRankingPrompt(String movieList, String userPreference) {
        try {
            ClassPathResource resource = new ClassPathResource("prompts/movie-ranking.txt");
            Reader reader =
                    new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
            String promptTemplate = FileCopyUtils.copyToString(reader);
            String effectivePreference = (userPreference != null) ? userPreference : "";
            return promptTemplate
                    .replace("{movie_list}", movieList)
                    .replace("{user_preference}", effectivePreference);
        } catch (Exception e) {
            throw new RuntimeException("프롬프트 파일 로드 실패", e);
        }
    }

    // 새롭게 추가된 영화 Q&A 프롬프트 메서드 (movie-qa.txt.txt)
    public String getMovieQAPrompt(String question) {
        try {
            ClassPathResource resource = new ClassPathResource("prompts/movie-qa.txt");
            Reader reader =
                    new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
            String promptTemplate = FileCopyUtils.copyToString(reader);
            return promptTemplate.replace("{question}", question);
        } catch (Exception e) {
            throw new RuntimeException("QA 프롬프트 파일 로드 실패", e);
        }
    }
}
