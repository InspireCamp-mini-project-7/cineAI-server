package com.amcamp.cineAI.domain.movie.dto.response;

import com.amcamp.cineAI.domain.movie.domain.Movie;
import java.util.List;

public record MovieInfoResponse(
        Long id,
        String title,
        String posterImageUrl,
        List<String> directorName,
        List<String> castsList,
        String nation,
        String plot,
        List<String> genreList,
        String releaseDate) {
    public static MovieInfoResponse of(Movie movie) {
        return new MovieInfoResponse(
                movie.getId(),
                movie.getTitle(),
                movie.getPosterImageUrl(),
                movie.getDirectorName(),
                movie.getCastsList(),
                movie.getNation(),
                movie.getPlot(),
                movie.getGenreList(),
                movie.getReleaseDate());
    }
}
