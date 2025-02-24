package com.amcamp.cineAI.domain.movie.api;

import com.amcamp.cineAI.domain.movie.application.MovieService;
import com.amcamp.cineAI.domain.movie.dto.request.NewMovieCreateRequest;
import com.amcamp.cineAI.domain.movie.dto.response.BasicMovieInfoResponse;
import com.amcamp.cineAI.domain.movie.dto.response.MovieInfoResponse;
import com.amcamp.cineAI.domain.movie.dto.response.MovieInfoResponseList;
import com.amcamp.cineAI.global.error.exception.CustomException;
import com.amcamp.cineAI.global.error.exception.ErrorCode;
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
    public List<BasicMovieInfoResponse> movieInit(@RequestParam(required = true) int size) {
        return movieService.getMovieInitInfo(size);
    }

    @Operation(summary = "관심 있는 영화 반응 업데이트 ", description = "사용자가 관심을 표시한 영화 반응을 업데이트합니다")
    @PatchMapping("/liked")
    public ResponseEntity<Void> movieLikedUpdate(@RequestParam(required = true) Long movieId) {
        movieService.updateMovieLikedStatus(movieId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "영화 검색 기능", description = "검색 키워드를 제목, 배우명, 감독명, 장르의 키워드로 검색합니다.")
    @GetMapping("/search")
    public MovieInfoResponseList movieSearch(
            @RequestParam(required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page, // 페이지 기본값은 0
            @RequestParam(value = "size", defaultValue = "5") int size) { // 페이지 크기 기본값은 3
        int offset = page * size;

        // 서비스에서 searchMovies 메서드 호출
        return movieService.searchMovies(keyword, size, offset);
    }

    @Operation(summary = "영화 DB 초기 데이터 세팅", description = "KMDB의 데이터 CSV파일을 이용하여 초기 데이터 세팅")
    @GetMapping("/upload")
    public ResponseEntity<Void> movieDbInit() {
        try {
            movieService.uploadCSV();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new CustomException(ErrorCode.CSV_NOT_UPLOAD);
        }
    }

    @Operation(
            summary = "영화 추천",
            description = "현재 로그인한 회원의 선호 정보를 가져와 LLM을 호출하여 검색어 생성, 생성된 검색어로 DB에서 영화 정보 조회")
    @GetMapping("/todays")
    public ResponseEntity<Slice<BasicMovieInfoResponse>> getTodaysMovies(
            @Parameter(description = "이전 페이지의 마지막 영화 ID (첫 페이지는 비워두세요)")
                    @RequestParam(required = false)
                    Long lastMovieId,
            @RequestParam(value = "size", defaultValue = "3") int pageSize) {
        Slice<BasicMovieInfoResponse> response =
                movieService.getTodaysMoviePreferences(lastMovieId, pageSize);
        return ResponseEntity.ok().body(response);
    }
}
