package com.amcamp.cineAI.domain.movie.dto.request;

import java.util.List;

public record NewMovieCreateRequest(
        String title,
        String posterImageUrl,
        List<String> directorName,
        List<String> castList,
        String nation,
        String plot,
        List<String> genreList,
        String releaseDate) {}
