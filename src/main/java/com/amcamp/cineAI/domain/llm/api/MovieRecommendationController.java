package com.amcamp.cineAI.domain.llm.api;

import com.amcamp.cineAI.domain.llm.application.MovieRecommendationService;
import com.amcamp.cineAI.domain.llm.dto.request.RankingRequest;
import com.amcamp.cineAI.domain.llm.dto.response.MovieRecommendationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MovieRecommendationController {

    private final MovieRecommendationService recommendationService;

    @PostMapping("/api/movies/recommend")
    public MovieRecommendationResponse getMovieRanking(@RequestBody RankingRequest request) {
        String result =
                recommendationService.getRankingResult(
                        request.getMovieList(), request.getUserPreference());
        return new MovieRecommendationResponse(result);
    }
}
