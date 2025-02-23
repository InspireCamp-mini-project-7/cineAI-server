package com.amcamp.cineAI.domain.movie.dto.request;

import java.util.List;

public record NewMovieCreateRequest(
        String title,
        String posterImageUrl,
        String directorName,
        List<String> castList,
        String nation,
        String plot,
        List<String> genreList,
        Long accAudiences,
        String releaseYear) {}
