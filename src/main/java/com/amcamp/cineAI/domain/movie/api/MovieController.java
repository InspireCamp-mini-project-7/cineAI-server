package com.amcamp.cineAI.domain.movie.api;

import com.amcamp.cineAI.domain.movie.application.MovieService;
import com.amcamp.cineAI.domain.movie.dto.request.NewMovieCreateRequest;
import com.amcamp.cineAI.domain.movie.dto.response.BasicMovieInfoResponse;
import com.amcamp.cineAI.domain.movie.dto.response.MovieInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "영화 API", description = "영화 관련 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    @Operation(summary = "최신 영화 추가", description = "최신 영화를 추가합니다.")
    @PostMapping("/create")
    public ResponseEntity<Void> movieCreate(@Valid @RequestBody NewMovieCreateRequest request) {
        movieService.createMovie(request);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "최신 영화 목록 조회", description = "등록된 최신 영화 정보를 조회합니다.")
    @GetMapping("/new-movies")
    public Slice<BasicMovieInfoResponse> newMovieList(
            @Parameter(description = "이전 페이지의 마지막 영화 ID (첫 페이지는 비워두세요)")
                    @RequestParam(required = false)
                    Long lastMovieId,
            @RequestParam(value = "size", defaultValue = "3") int pageSize) {
        return movieService.getNewMovieList(lastMovieId, pageSize);
    }

    @Operation(summary = "영화 상세 조회", description = "등록된 최신 영화 정보를 조회합니다.")
    @GetMapping("/{movieId}")
    public MovieInfoResponse movieInfo(@PathVariable Long movieId) {
        return movieService.getMovieInfo(movieId);
    }

    @Operation(
            summary = "초기 설정: 영화 시청 반응 추가 대상 영화 조회",
            description = "사용자에게 초기 설정을 위한 영화 목록을 제공합니다")
    @GetMapping("/init")
    public List<BasicMovieInfoResponse> movieInit(@RequestParam(required = false) int size) {
        return movieService.getMovieInitInfo(size);
    }

    @Operation(summary = "관심 있는 영화 반응 업데이트 ", description = "사용자가 관심을 표시한 영화 반응을 업데이트합니다")
    @PatchMapping("/liked")
    public ResponseEntity<Void> movieLikedUpdate(@RequestParam(required = true) Long movieId) {
        movieService.updateMovieLikedStatus(movieId);
        return ResponseEntity.ok().build();
    }
}
