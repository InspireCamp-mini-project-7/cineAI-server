package com.amcamp.cineAI.domain.movie.dao;

import com.amcamp.cineAI.domain.movie.dto.response.BasicMovieInfoResponse;
import org.springframework.data.domain.Slice;

public interface MoviePreferenceRepositoryCustom {
    Slice<BasicMovieInfoResponse> findMemberLikedMovie(
            Long lastMovieId, int pageSize, Long memberId);
}
