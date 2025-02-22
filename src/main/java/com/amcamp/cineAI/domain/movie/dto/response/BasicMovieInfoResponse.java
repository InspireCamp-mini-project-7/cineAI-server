package com.amcamp.cineAI.domain.movie.dto.response;

public record BasicMovieInfoResponse(
        String title, String posterImageUrl, String[] genreList, String releaseYear) {}
