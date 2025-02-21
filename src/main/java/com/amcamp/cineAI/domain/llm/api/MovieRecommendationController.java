package com.amcamp.cineAI.domain.llm.api;

import com.amcamp.cineAI.domain.llm.application.MovieRecommendationService;
import com.amcamp.cineAI.domain.llm.dto.request.RankingRequest;
import com.amcamp.cineAI.global.common.response.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movies/recommend")
public class MovieRecommendationController {

    private final MovieRecommendationService recommendationService;

    @Autowired
    public MovieRecommendationController(MovieRecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @PostMapping
    public ResponseEntity<CommonResponse<String>> getMovieRanking(
            @RequestBody RankingRequest request) {
        String result =
                recommendationService.getRankingResult(
                        request.getMovieList(), request.getUserPreference());
        if ("검색된 영화가 없습니다".equals(result)) {
            return ResponseEntity.ok(CommonResponse.onFailure(404, result));
        }
        return ResponseEntity.ok(CommonResponse.onSuccess(200, result));
    }
}
