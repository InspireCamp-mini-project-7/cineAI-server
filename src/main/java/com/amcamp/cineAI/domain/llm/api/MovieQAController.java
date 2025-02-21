package com.amcamp.cineAI.domain.llm.api;

import com.amcamp.cineAI.domain.llm.application.MovieQAService;
import com.amcamp.cineAI.domain.llm.dto.request.QARequest;
import com.amcamp.cineAI.global.common.response.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movies/qa")
public class MovieQAController {

    private final MovieQAService qaService;

    @Autowired
    public MovieQAController(MovieQAService qaService) {
        this.qaService = qaService;
    }

    @PostMapping
    public ResponseEntity<CommonResponse<String>> getMovieAnswer(@RequestBody QARequest request) {
        String answer = qaService.getAnswer(request.getQuestion());
        return ResponseEntity.ok(CommonResponse.onSuccess(200, answer));
    }
}
