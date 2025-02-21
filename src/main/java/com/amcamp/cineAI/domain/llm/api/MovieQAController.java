package com.amcamp.cineAI.domain.llm.api;

import com.amcamp.cineAI.domain.llm.application.MovieQAService;
import com.amcamp.cineAI.domain.llm.dto.request.QARequest;
import com.amcamp.cineAI.domain.llm.dto.response.MovieAnswerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MovieQAController {

    private final MovieQAService qaService;

    @PostMapping("/api/movies/qa")
    public MovieAnswerResponse getMovieAnswer(@RequestBody QARequest request) {
        String answer = qaService.getAnswer(request.getQuestion());
        return new MovieAnswerResponse(answer);
    }
}
