package com.amcamp.cineAI.domain.movie.dto.response;

import com.amcamp.cineAI.domain.movie.domain.MoviePreference;

public record MoviePreferenceResponse(Long id, Long movieId, String likedStatus) {
    public static MoviePreferenceResponse from(MoviePreference preference) {
        return new MoviePreferenceResponse(
                preference.getId(), preference.getMovie().getId(), preference.getLiked().name());
    }
}
