package com.amcamp.cineAI.domain.movie.dto.request;

public record NewMovieCreateRequest(
        String title,
        String posterImageUrl,
        String directorName,
        String[] castList,
        String nation,
        String plot,
        String[] genreList,
        Long accAudiences,
        String releaseYear) {}
