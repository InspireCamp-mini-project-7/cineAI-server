package com.amcamp.cineAI.domain.movie.application;

import com.amcamp.cineAI.domain.movie.dao.MovieRepository;
import com.amcamp.cineAI.domain.movie.domain.Movie;
import com.amcamp.cineAI.domain.movie.dto.request.NewMovieCreateRequest;
import com.amcamp.cineAI.domain.movie.dto.response.BasicMovieInfoResponse;
import com.amcamp.cineAI.domain.movie.dto.response.MovieInfoResponse;
import com.amcamp.cineAI.global.error.exception.CustomException;
import com.amcamp.cineAI.global.error.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    public void createMovie(NewMovieCreateRequest request) {
        Movie newMovie = Movie.createMovie(request);
        movieRepository.save(newMovie);
    }

    @Transactional(readOnly = true)
    public Slice<BasicMovieInfoResponse> getNewMovieList(Long lastMovieId, int pageSize) {
        return movieRepository.findAllNewMovie(lastMovieId, pageSize);
    }

    @Transactional(readOnly = true)
    public MovieInfoResponse getMovieInfo(Long movieId) {
        Movie movie =
                movieRepository
                        .findById(movieId)
                        .orElseThrow(() -> new CustomException(ErrorCode.MOVIE_NOT_FOUND)); // 예외 던짐
        return MovieInfoResponse.of(movie);
    }
}
