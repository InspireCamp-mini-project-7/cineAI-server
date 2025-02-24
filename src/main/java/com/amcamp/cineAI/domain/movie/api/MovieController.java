package com.amcamp.cineAI.domain.movie.api;

import com.amcamp.cineAI.domain.movie.application.MovieService;
import com.amcamp.cineAI.domain.movie.dao.MovieRepository;
import com.amcamp.cineAI.domain.movie.domain.Movie;
import com.amcamp.cineAI.domain.movie.domain.MovieStatus;
import com.amcamp.cineAI.domain.movie.dto.request.MovieCreateForm;
import com.amcamp.cineAI.domain.movie.dto.request.NewMovieCreateRequest;
import com.amcamp.cineAI.domain.movie.dto.response.BasicMovieInfoResponse;
import com.amcamp.cineAI.domain.movie.dto.response.MovieInfoResponse;
import com.amcamp.cineAI.domain.movie.dto.response.MovieInfoResponseList;
import com.amcamp.cineAI.global.error.exception.CustomException;
import com.amcamp.cineAI.global.error.exception.ErrorCode;
import com.amcamp.cineAI.global.util.FileUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

@Tag(name = "영화 API", description = "영화 관련 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;
    private final MovieRepository movieRepository;
    private final FileUtils fileUtils;

    @Operation(summary = "최신 영화 추가", description = "최신 영화를 추가합니다.")
    @PostMapping("/create")
    public ResponseEntity<Void> movieCreate(@ModelAttribute MovieCreateForm form) {
        MultipartFile posterImage = form.getPosterImage();
        NewMovieCreateRequest request =
                new NewMovieCreateRequest(
                        form.getTitle(),
                        null,
                        form.getDirectorName(),
                        form.getCastList(),
                        form.getNation(),
                        form.getPlot(),
                        form.getGenreList(),
                        form.getReleaseDate());
        movieService.createMovie(request, posterImage);
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

    @Operation(summary = "영화 포스터 다운로드", description = "영화 포스터를 다운로드합니다.")
    @GetMapping("{movieId}/poster")
    public ResponseEntity<Resource> moviePosterDownload(@PathVariable Long movieId) {
        Movie movie =
                movieRepository
                        .findById(movieId)
                        .orElseThrow(() -> new CustomException(ErrorCode.MOVIE_NOT_FOUND));
        String posterImageUrl = movie.getPosterImageUrl();
        String title = movie.getTitle();
        MovieStatus status = movie.getStatus();
        String fileExtension = fileUtils.extractExt(posterImageUrl);

        try {
            if (status.equals(MovieStatus.CREATED)) {
                posterImageUrl = fileUtils.generatePresignedUrl(posterImageUrl);
            }
            InputStream imageInputStream = fileUtils.getImageInputStream(posterImageUrl);

            String contentType = "image/" + fileExtension; // 이미지의 Content-Type 설정

            // 파일 이름 인코딩
            String encodedFileName =
                    UriUtils.encode(title + "." + fileExtension, StandardCharsets.UTF_8);
            String contentDisposition = "attachment; filename=\"" + encodedFileName + "\"";

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                    .header(HttpHeaders.CONTENT_TYPE, contentType)
                    .body(new InputStreamResource(imageInputStream));
        } catch (Exception e) {
            throw new CustomException(ErrorCode.POSTER_NOT_FOUND);
        }
    }
}
