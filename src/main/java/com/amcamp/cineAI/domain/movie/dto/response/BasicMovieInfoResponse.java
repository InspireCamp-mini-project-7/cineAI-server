package com.amcamp.cineAI.domain.movie.dto.response;

import com.amcamp.cineAI.domain.movie.domain.Movie;

public record BasicMovieInfoResponse(
        Long movieId, String title, String posterImageUrl, String[] genreList, String releaseYear) {
    public static BasicMovieInfoResponse of(Movie movie) {
        return new BasicMovieInfoResponse(
                movie.getId(),
                movie.getTitle(),
                movie.getPosterImageUrl(),
                movie.getGenreList(),
                movie.getReleaseYear());
    }
}
