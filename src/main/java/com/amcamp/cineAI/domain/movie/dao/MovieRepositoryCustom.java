package com.amcamp.cineAI.domain.movie.dao;

import com.amcamp.cineAI.domain.movie.domain.Movie;
import com.amcamp.cineAI.domain.movie.dto.response.BasicMovieInfoResponse;
import java.util.List;
import org.springframework.data.domain.Slice;

public interface MovieRepositoryCustom {
    Slice<BasicMovieInfoResponse> findAllNewMovie(Long lastMovieId, int pageSize);

    List<Movie> searchMoviesByKeywords(List<String> keywords, Long lastMovieId, int pageSize);
}
