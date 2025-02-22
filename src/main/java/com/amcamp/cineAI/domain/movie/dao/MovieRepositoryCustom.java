package com.amcamp.cineAI.domain.movie.dao;

import com.amcamp.cineAI.domain.movie.dto.response.BasicMovieInfoResponse;
import org.springframework.data.domain.Slice;

public interface MovieRepositoryCustom {
    Slice<BasicMovieInfoResponse> findAllNewMovie(Long lastMovieId, int pageSize);
}
