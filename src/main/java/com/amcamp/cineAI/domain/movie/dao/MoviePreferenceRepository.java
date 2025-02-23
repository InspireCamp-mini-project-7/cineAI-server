package com.amcamp.cineAI.domain.movie.dao;

import com.amcamp.cineAI.domain.movie.domain.MoviePreference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoviePreferenceRepository
        extends JpaRepository<MoviePreference, Long>, MoviePreferenceRepositoryCustom {
    MoviePreference findByMovieIdAndMemberId(Long movieId, Long memberId);
}
